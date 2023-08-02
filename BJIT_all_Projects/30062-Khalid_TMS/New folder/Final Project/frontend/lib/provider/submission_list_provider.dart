import 'dart:convert';

import 'package:flutter/cupertino.dart';
import "package:universal_html/html.dart" as html;
import 'package:http/http.dart' as http;


class SubmissionListProvider with ChangeNotifier {

  Future<List<dynamic>> getSubmissionsByAssignmentId() async {

    String? token = getTokenFromLocalStorage();

    String assignmentId = getAssignmentIdFromLocalStorage().toString();

    String url = "http://localhost:8090/assignment/submission-list/$assignmentId";

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

  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

  void saveAssignmentIdInLocalStorage(String assignmentId) {
    final storage = html.window.localStorage;
    storage['assignmentId'] = assignmentId;
  }

  String? getAssignmentIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['assignmentId'];
  }

}