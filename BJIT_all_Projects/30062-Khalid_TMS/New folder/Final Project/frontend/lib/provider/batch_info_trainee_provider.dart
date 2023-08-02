import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:universal_html/html.dart' as html;
import 'package:http/http.dart' as http;

class BatchInfoTraineeProvider with ChangeNotifier {


  Future<void> getTraineeIdByUserId() async {

    String? token = getTokenFromLocalStorage();
    int userId = int.parse(getUserIdFromLocalStorage()!);

    print("----------> user id $userId");

    if (token != null) {
    } else {
      print('The token is not available. Handle the user being logged out or not logged in');
    }

    String url = "http://localhost:8090/trainee/user/$userId";



    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );


    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      int batchId = data["batchId"];
      int traineeId = data["traineeId"];

      print("batchId---------------> $batchId");

      saveTraineeIdInLocalStorage(traineeId.toString());
      saveBatchIdInLocalStorage(batchId.toString());

    }

  }

  void saveTokenToLocalStorage(String token) {
    final storage = html.window.localStorage;
    storage['token'] = token;
  }
  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }
  String? getUserIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['userId'];

  }
  void saveBatchIdInLocalStorage(String batchId) {
    final storage = html.window.localStorage;
    storage['batchId'] = batchId;
  }

  void saveTraineeIdInLocalStorage(String traineeId) {
    final storage = html.window.localStorage;
    storage['traineeId'] = traineeId;
  }
  String? getTraineeIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['traineeId'];

  }
}