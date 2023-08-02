import 'dart:convert';

import "package:universal_html/html.dart" as html;
import 'package:http/http.dart' as http;
import 'package:flutter/cupertino.dart';

class CourseInfoProvider with ChangeNotifier {

  Future<List<dynamic>> getCoursesByBatchId() async {

    String? token = getTokenFromLocalStorage();
    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);

    String url = "http://localhost:8090/course/batch/$tempBatchId";

    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );

    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      return data;
    }
    return [];
  }

  Future<void> updateCourse(var courseInfo, BuildContext context) async {

    String? token = getTokenFromLocalStorage();

    String courseId =  courseInfo['courseId'];

    String? url = 'http://localhost:8090/course/update/$courseId';

    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };

    String? getBatchIdInLocalStorage() {
      final storage = html.window.localStorage;
      return storage['batchId'];
    }
    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);

    final data = {
      "courseName": courseInfo['courseName'],
      "description": courseInfo['description'],
      "startDate": courseInfo['startDate'],
      "endDate": courseInfo['endDate'],

    };

    final jsonBody = jsonEncode(data);
    final response =
    await http.put(Uri.parse(url), headers: headers, body: jsonBody);

    if (response.statusCode == 200) {
      notifyListeners();
      Navigator.pop(context);
    }
  }


  String? getBatchIdInLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }

  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

}