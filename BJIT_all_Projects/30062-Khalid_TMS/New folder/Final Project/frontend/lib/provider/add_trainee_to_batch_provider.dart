
import 'package:flutter/cupertino.dart';
import 'dart:convert';
import "package:universal_html/html.dart" as html;
import 'package:http/http.dart' as http;

class AddTraineeToBatchProvider with ChangeNotifier {

  List<int> selectedTrainees = [];

  // void addToSelectedTrainees(int c){
  //   selectedTrainees.add(c);
  //   notifyListeners();
  // }
  //
  // bool isSelected(int c){
  //   return selectedTrainees.contains(c);
  // }

  Future<List<dynamic>> getTrainees() async {

    String? token = getTokenFromLocalStorage();

    if (token != null) {
      print('token ase- $token');
    } else {
      print('The token is not available. Handle the user being logged out or not logged in');
    }

    String url = "http://localhost:8090/trainee/unassigned";
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

  Future<void> postTraineeToBatch(var batchInfo) async {

    String? token = getTokenFromLocalStorage();
    String? url = 'http://localhost:8090/trainee/batch-assign';

    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };
    final data = {
      "traineeId": batchInfo['traineeId'],
      "batchId": batchInfo['batchId'],
    };

    final jsonBody = jsonEncode(data);

    //print(jsonBody);
    final response = await http.post(Uri.parse(url), headers: headers, body: jsonBody);

    if (response.statusCode == 200) {
      print(response.body);
      print('Data posted successfully after return');
      notifyListeners();
    } else {
      print('Error occurred while posting data: ${response.body}');
    }
  }

}