import 'dart:convert';

import "package:universal_html/html.dart" as html;
import 'package:http/http.dart' as http;
import 'package:flutter/cupertino.dart';

class ScheduleProvider with ChangeNotifier {

  Future<List<dynamic>> getScheduleByBatchId() async {

    String? token = getTokenFromLocalStorage();
    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);

    String url = "http://localhost:8090/course/schedule/$tempBatchId";

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




  String? getBatchIdInLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }

  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

}



















































