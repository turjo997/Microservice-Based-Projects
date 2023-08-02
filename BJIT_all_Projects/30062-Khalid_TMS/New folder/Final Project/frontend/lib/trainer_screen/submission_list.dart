import 'dart:html';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:training_management_system/provider/submission_list_provider.dart';

class SubmissionList extends StatefulWidget {
  const SubmissionList({Key? key}) : super(key: key);

  @override
  _SubmissionListState createState() => _SubmissionListState();
}

class _SubmissionListState extends State<SubmissionList> {

  @override
  Widget build(BuildContext context) {
    return Consumer<SubmissionListProvider>(
      builder: (context, submissionListProvider, child) {
        return Column(
          children: [
            Expanded(
                flex: 1,
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(0, 15, 0, 0),
                  child: Text("Submissions",
                    style: TextStyle(
                      fontSize: 30,
                      fontWeight: FontWeight.w900,
                    ),
                  ),
                )
            ),
            Expanded(
              flex: 9,
              child: Container(
                child: FutureBuilder<List<dynamic>>(
                  future: submissionListProvider.getSubmissionsByAssignmentId(),
                  builder: (context, snapshot) {
                    if (snapshot.connectionState == ConnectionState.waiting) {
                      return Center(
                        child: CircularProgressIndicator(),
                      );
                    } else if (snapshot.hasError || !snapshot.hasData) {
                      return Center(
                        child: Text("No submissions found."),
                      );
                    } else {
                      List<dynamic> submissions = snapshot.data!;
                      return ListView.builder(
                        itemCount: submissions.length,
                        itemBuilder: (context, index) {
                          var submission = submissions[index];
                          return Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),
                              child: ListTile(
                                title: Text(
                                  submission['assignmentTitle'],
                                  style: TextStyle(fontWeight: FontWeight.bold),
                                ),
                                subtitle: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      "Trainee Name: ${submission['traineeName']}",
                                      style: TextStyle(color: Colors.black),
                                    ),
                                    Text(
                                      "Trainee ID: ${submission['traineeId']}",
                                      style: TextStyle(color: Colors.black),
                                    ),
                                    Text(
                                      "Submission Date: ${submission['submissionTime']}",
                                      style: TextStyle(color: Colors.black),
                                    ),
                                  ],
                                ),
                                trailing: ElevatedButton(
                                  onPressed: () {
                                    String fileUrl = 'http://localhost:8090/assignment/submission/download-file/${submission["submissionId"]}';
                                    downloadFile(fileUrl);
                                  },
                                  child: Text('Download'),
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
            ),
          ],
        );
      },
    );
  }

  void downloadFile(String fileUrl) {
    AnchorElement anchor = AnchorElement(href: fileUrl)
      ..target = '_blank'
      ..download = '';
    anchor.click();
  }

}
