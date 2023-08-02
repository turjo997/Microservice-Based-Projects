import 'dart:convert';

import 'package:universal_html/html.dart' as html;
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

class TraineeMenuProvider with ChangeNotifier{
  bool profile = false;
  bool batch = true;
  bool logout = false;

  void onClickSideMenu(int c){
    profile = false;
    batch = false;
    logout = false;
    if(c == 1) profile = true;
    if(c == 2) batch = true;
    if(c == 3) logout = true;
    notifyListeners();

  }

  void saveTokenToLocalStorage(String token) {
    final storage = html.window.localStorage;
    storage['token'] = token;
    notifyListeners();
  }

  String? getTokenFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['token'];
  }

  void saveRoleToLocalStorage(String role) {
    final storage = html.window.localStorage;
    storage['role'] = role;
    notifyListeners();
  }

  String? getRoleFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['role'];
  }

  void saveBatchIdInLocalStorage(String batchId) {
    final storage = html.window.localStorage;
    storage['batchId'] = batchId;
  }

  void saveTraineeIdInLocalStorage(String traineeId) {
    final storage = html.window.localStorage;
    storage['traineeId'] = traineeId;
  }

  String? getUserIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['userId'];

  }

  Future<void> getTraineeIdByUserId() async {

    String? token = getTokenFromLocalStorage();
    int userId = int.parse(getUserIdFromLocalStorage()!);

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

      print("batchId------sdf;og BABNAABBABA---------> $batchId");
      print("tia;lkasfgkla kkldsahg $traineeId");

      saveTraineeIdInLocalStorage(traineeId.toString());
      saveBatchIdInLocalStorage(batchId.toString());

    }

  }

}