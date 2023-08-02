import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'dart:convert';
import "package:universal_html/html.dart" as html;
import 'package:http/http.dart' as http;

class AddTrainerToBatchProvider with ChangeNotifier {

  List<int> selectedTrainers = [];


  Future<List<dynamic>> getTrainers() async {

    String? token = getTokenFromLocalStorage();

    if (token != null) {
      print('token ase- $token');
    } else {
      print('The token is not available. Handle the user being logged out or not logged in');
    }

    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);

    String url = "http://localhost:8090/trainer/unassigned/$tempBatchId";
    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );

    print(response.statusCode);
    print(response.body);

    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      print("Trainers data  --- $data");
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

  Future<void> postTrainerToBatch(var batchInfo) async {

    String? token = getTokenFromLocalStorage();
    String? url = 'http://localhost:8090/batch/trainer-assign';

    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };
    final data = {
      "trainerId": batchInfo['trainerId'],
      "batchId": batchInfo['batchId'],
    };

    final jsonBody = jsonEncode(data);
    final response = await http.post(Uri.parse(url), headers: headers, body: jsonBody);

    if (response.statusCode == 200) {
      print(response.body);
      print('Data posted successfully');
      notifyListeners();
    } else {
      // Error occurred while posting data
      print('Error occurred while posting data: ${response.body}');
    }
  }

}