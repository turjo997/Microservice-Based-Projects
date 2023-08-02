import 'package:flutter/cupertino.dart';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart' as http;
import "package:universal_html/html.dart" as html;


class AdminBatchInfoProvider with ChangeNotifier {

  Future<void> postCourse(var courseInfo, BuildContext context) async {

    String? token = getTokenFromLocalStorage();
    String? url = 'http://localhost:8090/course/create';

    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };

    String? getBatchIdInLocalStorage() {
      final storage = html.window.localStorage;
      return storage['batchId'];
    }

    String tempBatchId =  getBatchIdInLocalStorage()!;

    print("batch course------------------->$tempBatchId");

    final data = {
      "courseName": courseInfo['courseName'],
      "description": courseInfo['description'],
      "startDate": courseInfo['startDate'],
      "endDate": courseInfo['endDate'],
      "batchId": tempBatchId,

    };
    final jsonBody = jsonEncode(data);
    final response = await http.post(Uri.parse(url), headers: headers, body: jsonBody);

    if (response.statusCode == 200) {
      print(response.body);
      print('Data posted successfully');
      Navigator.pop(context);
    } else {
      // Error occurred while posting data
      print('Error occurred while posting data: ${response.body}');
    }
  }

  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

  String dateFormatter(String date){
    return DateFormat('dd MMMM, yyyy').format(DateTime.parse(date));

  }

}