import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import "package:universal_html/html.dart" as html;

class AdminDashboardProvider with ChangeNotifier{

  Future<void> postBatch(var batchInfo, BuildContext context) async {
    //List categoryDataList, BuildContext context;
    //print(categoryDataList[0]);

    String? token = getTokenFromLocalStorage();
    String? url = 'http://localhost:8090/batch/create';
    //print(url!+token!);
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };
    final data = {
      "batchName": batchInfo['name'],
      "startDate": batchInfo['start_date'],
      "endDate": batchInfo['end_date'],
      "trainers": null,

    };
    final jsonBody = jsonEncode(data);
    final response =
    await http.post(Uri.parse(url), headers: headers, body: jsonBody);

    if (response.statusCode == 200) {
      print(response.body);
      // Data posted successfully
      print('Data posted successfully');
      notifyListeners();
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

  void saveBatchIdInLocalStorage(String batchId) {
    final storage = html.window.localStorage;
    storage['batchId'] = batchId;
  }

  String? getBatchIdInLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }





  Future<List<dynamic>> getBatch() async {

    String? token = getTokenFromLocalStorage();
    //print(token);
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

    const String url = "http://localhost:8090/batch";
    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );

    print(response.statusCode);
    print(response.body);
    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      print(data);
      return data;
    }
    return [];
  }

  Future<List<dynamic>> getBatchById(int batchId) async {

    String? token = getTokenFromLocalStorage();

    if (token != null) {
      print('token ase- $token');
      // Use the token for your API calls
    } else {
      print('The token is not available. Handle the user being logged out or not logged in');
    }
    print("ami call hochhi");

    String url = "http://localhost:8090/batch/$batchId";

    final response = await http.get(
      Uri.parse(url),
      headers: {"Authorization": "Bearer $token"},
    );

    print(response.statusCode);
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






}