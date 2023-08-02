import 'dart:convert';

import 'package:flutter/cupertino.dart';
import "package:universal_html/html.dart" as html;
import 'package:http/http.dart' as http;


class BatchDetailsProvider with ChangeNotifier {

  Future<dynamic> getBatchByBatchId() async {

    String? token = getTokenFromLocalStorage();
    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);

    String url = "http://localhost:8090/batch/$tempBatchId";

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


  Future<List<dynamic>> getTrainerListByBatchId() async {

    String? token = getTokenFromLocalStorage();
    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);
    String url = "http://localhost:8090/batch/trainers/$tempBatchId";

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


  Future<List<dynamic>> getTraineeListByBatchId() async {

    String? token = getTokenFromLocalStorage();
    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);

    String url = "http://localhost:8090/trainee/trainee-list/$tempBatchId";

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

  Future<void> updateBatch(var batchInfo, BuildContext context) async {
    String? token = html.window.localStorage['token'];
    int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);

    String? url = 'http://localhost:8090/batch/update/$tempBatchId';

    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };
    final data = {
      "batchName": batchInfo['name'],
      "startDate": batchInfo['start_date'],
      "endDate": batchInfo['end_date'],
    };

    final jsonBody = jsonEncode(data);
    final response =
    await http.put(Uri.parse(url), headers: headers, body: jsonBody);


    if (response.statusCode == 200) {
      notifyListeners();
      Navigator.pop(context);
    }
  }

  String? getRoleFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['role'];

  }
}