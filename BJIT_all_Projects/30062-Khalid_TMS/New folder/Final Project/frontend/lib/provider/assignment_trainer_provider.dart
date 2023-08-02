import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:convert';
import 'dart:typed_data';
import 'package:http/http.dart' as http;
import 'package:universal_html/html.dart' as html;
import 'package:file_picker/file_picker.dart';

class AssignmentTrainerProvider with ChangeNotifier {
  var assignmentFileName = "";
  var assignment;

  String trainerId = '';
  String batchId = '';

  Future<void> createAssignment(var adminData, BuildContext context) async {
    String? token = getTokenFromLocalStorage();
    String? url = 'http://localhost:8090/assignment/create';

    final headers = {
      'Authorization': 'Bearer $token',
    };

    var request = http.MultipartRequest('POST', Uri.parse(url));
    request.headers.addAll(headers);

    try {
      if (assignment != null) {
        final assignmentFile = await http.MultipartFile.fromBytes(
            'assignmentMultipartFile', assignment,
            filename: assignmentFileName);
        request.files.add(assignmentFile);
      } else {}
    } catch (e) {}

    trainerId = getTrainerIdFromLocalStorage()!;
    batchId = getBatchIdInLocalStorage()!;

    request.fields['title'] = adminData['title'];
    request.fields['description'] = adminData['description'];
    request.fields['deadline'] = adminData['deadline'];
    request.fields['trainerId'] = trainerId; //['trainerId'];
    request.fields['batchId'] = batchId; //['batchId'];

    var response = await request.send();

    if (response.statusCode == 200) {
      String responseString = await response.stream.bytesToString();
      var jsonResponse = jsonDecode(responseString);
      print('Assignment Created successfully');
      notifyListeners();
      Navigator.pop(context);
    } else {
      print(response.statusCode);
    }
  }

  ///submission----------------

  Future<void> createSubmission(BuildContext context) async {
    String? token = getTokenFromLocalStorage();
    String? url = 'http://localhost:8090/assignment/submit';

    final headers = {
      'Authorization': 'Bearer $token',
    };

    var request = http.MultipartRequest('POST', Uri.parse(url));
    request.headers.addAll(headers);

    if (assignment != null) {
      final submissionFile = http.MultipartFile.fromBytes(
          'submissionMultipartFile', assignment,
          filename: assignmentFileName);
      request.files.add(submissionFile);
    } else {}

    String traineeId = getTraineeIdFromLocalStorage()!;
    String assignmentId = getAssignmentIdFromLocalStorage()!;

    request.fields['submissionTime'] = DateTime.now().toString();
    request.fields['assignmentId'] = assignmentId;
    request.fields['traineeId'] = traineeId;

    var response = await request.send();

    if (response.statusCode == 200) {
      String responseString = await response.stream.bytesToString();
      var jsonResponse = jsonDecode(responseString);
      print('Submission Created successfully');
      notifyListeners();
      Navigator.pop(context);
    } else {
      print(response.statusCode);
    }
  }

  Future<void> pickFile(BuildContext context) async {
    try {
      final result = await FilePicker.platform.pickFiles();
      assignment = result!.files.first.bytes;

      if (assignment == null) {
        const SnackBar(
          content: Text("NULL FILE"),
        );
      } else {
        assignmentFileName = result.files.first.name;
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(e.toString()),
        ),
      );
    }
  }

  Future<List<dynamic>> getAssignmentsByBatchId() async {
    String? token = getTokenFromLocalStorage();
    batchId = getBatchIdInLocalStorage()!;
    String url = "http://localhost:8090/assignment/batch/$batchId";

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

  String? getBatchIdInLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }

  String? getTrainerIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['trainerId'];
  }

  String? getTraineeIdFromLocalStorage() {
    final storage = html.window.localStorage;
    return storage['traineeId'];

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


