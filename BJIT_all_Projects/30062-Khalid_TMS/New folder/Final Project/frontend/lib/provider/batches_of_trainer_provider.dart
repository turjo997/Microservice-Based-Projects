import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:universal_html/html.dart' as html;
import 'package:flutter/material.dart';

class BatchesOfTrainerProvider with ChangeNotifier{

  int trainerId = -1;

  Future<void> getTrainerIdByUserId() async {

    String? token = getTokenFromLocalStorage();
    int userId = int.parse(getUserIdFromLocalStorage()!);

    if (token != null) {
      print('token ase- $token');
      // Use the token for your API calls
    } else {
      print('The token is not available. Handle the user being logged out or not logged in');
    }
    print("ami call hochhi");
    //final SharedPreferences prefs = await SharedPreferences.getInstance();
    // final String? token = await prefs.getString('token');
    //print("ami call hochhi na $token2");

    //token2 = token!;

     String url = "http://localhost:8090/trainer/user/$userId";
    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );

    print("response.statusCode getTrainerIdByUserId ${response.statusCode}");
    print(response.body);
    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      print(data);
     trainerId=data["trainerId"];
     print("traienr id  $trainerId");
      saveTrainerIdInLocalStorage(trainerId.toString());
     //notifyListeners();
    }

  }

  Future<List<dynamic>> getBatchListByTrainerId() async {
    await getTrainerIdByUserId();

    String? token = getTokenFromLocalStorage();
    //print(token);
    if (token != null) {
      print('token ase- $token');
      // Use the token for your API calls
    } else {
      print('The token is not available. Handle the user being logged out or not logged in');
    }


     String url = "http://localhost:8090/trainer/batch-list/$trainerId";
    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );

    print("response.statusCode----getBatchListByTrainerId ${response.statusCode}");
    print(response.body);
    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      print(data);

      return data;
    }
    return [];
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

  void saveTrainerIdInLocalStorage(String trainerId) {
    final storage = html.window.localStorage;
    storage['trainerId'] = trainerId;
  }

  String? getTrainerIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['trainerId'];

  }


}