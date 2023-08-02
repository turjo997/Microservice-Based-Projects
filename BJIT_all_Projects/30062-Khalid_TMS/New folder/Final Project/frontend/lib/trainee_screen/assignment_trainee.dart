import 'dart:html';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:quickalert/quickalert.dart';

import '../components/color.dart';
import '../provider/assignment_trainer_provider.dart';




class AssignmentTrainee extends StatelessWidget {

  final _submissionFormKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Consumer<AssignmentTrainerProvider>(
        builder: (context, assignmentProvider, child) {
          return Padding(

            padding: const EdgeInsets.all(8.0),
            child: Container(
              child: FutureBuilder<List<dynamic>>(
                future: assignmentProvider.getAssignmentsByBatchId(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return Center(
                      child: CircularProgressIndicator(),
                    );
                  } else if (snapshot.hasError || !snapshot.hasData) {
                    print(snapshot.data);
                    return Center(
                      child: Text("No assignments found."),
                    );
                  } else {
                    List<dynamic> assignments = snapshot.data!;
                    return ListView.builder(
                      itemCount: (assignments.length),
                      itemBuilder: (context, index) {
                        var assignment = assignments[index];
                        return Card(
                          color: Colors.grey[300],
                          // Grayish background color for the card
                          child: InkWell(
                            onTap: () {
                            },
                            child: Padding(
                              padding: const EdgeInsets.all(16.0),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    assignment["title"],
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 20,
                                    ),
                                  ),
                                  SizedBox(height: 8),
                                  Text(
                                    assignment["description"],
                                    style: TextStyle(
                                      fontSize: 16,
                                      color: Colors.grey[600],
                                    ),
                                  ),
                                  SizedBox(height: 8),
                                  Text(
                                    "Deadline: ${assignment["deadline"]}",
                                    style: TextStyle(
                                      fontSize: 14,
                                      color: Colors.grey[600],
                                    ),
                                  ),
                                  Padding(
                                    padding: const EdgeInsets.all(8.0),
                                    child: Row(
                                      mainAxisAlignment: MainAxisAlignment
                                          .spaceBetween,
                                      children: [
                                        ElevatedButton(
                                            onPressed: () async {
                                              String fileUrl = 'http://localhost:8090/assignment/download/${assignments[index]["assignmentId"]}';
                                              downloadFile(fileUrl);

                                            },
                                            child: Icon(Icons.download)),

                                        ElevatedButton(
                                            onPressed: () {
                                              QuickAlert.show(
                                                  onConfirmBtnTap: () {
                                                    if (_submissionFormKey.currentState!.validate()) {
                                                      assignmentProvider.createSubmission(context);
                                                    }
                                                  },
                                                  context: context,
                                                  type: QuickAlertType.info,
                                                  confirmBtnColor: sweetYellow,
                                                  confirmBtnText: "Submit",
                                                  title: "Submit Assignment",

                                                  widget: Form(
                                                    key: _submissionFormKey,
                                                    child: Container(
                                                      height: 100,
                                                      width: 500,
                                                      child: ElevatedButton(
                                                        onPressed: () {
                                                          assignmentProvider.saveAssignmentIdInLocalStorage(assignment["assignmentId"].toString());
                                                          assignmentProvider.pickFile(context);
                                                        },
                                                        child: assignmentProvider.assignment == null
                                                            ? Text("Select File")
                                                            : Icon(
                                                          Icons.check_box_rounded,
                                                          color: Colors.grey,
                                                        ),
                                                      ),
                                                    ),
                                                  ));
                                            },
                                            child: Icon(Icons.upload_file)
                                        ),
                                      ],
                                    ),
                                  ),
                                  // You can add the file widget here
                                ],
                              ),
                            ),
                          ),
                        );
                      },
                    );
                  }
                },
              ),
            ),
          );
        });
  }

  void downloadFile(String fileUrl) {
    AnchorElement anchor = AnchorElement(href: fileUrl)
      ..target = '_blank'
      ..download = '';
    anchor.click();
  }

}
