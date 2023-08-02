import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:quickalert/models/quickalert_type.dart';
import 'package:quickalert/widgets/quickalert_dialog.dart';

import '../components/color.dart';
import '../provider/notice_provider.dart';

class Notice extends StatelessWidget {
  Notice({Key? key}) : super(key: key);

  final _noticeFormKey = GlobalKey<FormState>();
  final _noticeTitle = TextEditingController();
  final _textData = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Consumer<NoticeProvider>(
      builder: (context, noticeProvider, child) {
        return Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
            title: Padding(
              padding: const EdgeInsets.fromLTRB(16, 25, 0, 20),
              child: Text(
                "Batch Notices",
                style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold),
              ),
            ),
            centerTitle: false,
          ),
          body: FutureBuilder<List<dynamic>>(
            future: noticeProvider.getAllNotice(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(child: CircularProgressIndicator());
              }
              if (snapshot.hasError) {
                return Center(child: Text("Something went wrong"));
              }
              if (snapshot.data!.isEmpty) {
                return Center(child: Text("No Notice"));
              }
              return ListView.builder(
                itemCount: snapshot.data!.length,
                itemBuilder: (context, index) {
                  return Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Card(
                      elevation: 4, // Add elevation for a subtle shadow effect
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(15),
                      ),
                      child: Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              "${snapshot.data![index]["noticeTitle"] ?? 'Untitled Notice'}",
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 24,
                              ),
                            ),
                            SizedBox(height: 8),
                            Text(
                              "${snapshot.data![index]["textData"]}",
                              style: TextStyle(
                                fontWeight: FontWeight.normal,
                                fontSize: 16,
                              ),
                            ),
                            SizedBox(height: 16),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Text(
                                  "Posted by: ${snapshot.data![index]["trainerName"] ?? 'Unknown'}",
                                  style: TextStyle(
                                    fontWeight: FontWeight.normal,
                                    fontSize: 14,
                                    color: Colors.grey,
                                  ),
                                ),
                                Text(
                                  "${snapshot.data![index]["postDate"]}",
                                  style: TextStyle(
                                    fontWeight: FontWeight.normal,
                                    fontSize: 14,
                                    color: Colors.grey,
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                    ),
                  );
                },
              );
            },
          ),
          floatingActionButton: FutureBuilder<String?>(
            future: Future.value(noticeProvider.getRoleFromLocalStorage()),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return Container();
              }

              if (snapshot.hasData && snapshot.data == "TRAINER") {
                return FloatingActionButton(
                  onPressed: () {
                    QuickAlert.show(
                        onConfirmBtnTap: () {
                          if (_noticeFormKey.currentState!.validate()) {
                            var noticeInfo = {
                              'noticeTitle': _noticeTitle.text,
                              'textData': _textData.text,
                            };

                            noticeProvider.createNotice(noticeInfo, context);
                          }
                        },
                        context: context,
                        type: QuickAlertType.info,
                        confirmBtnColor: sweetYellow,
                        confirmBtnText: "Create",
                        title: "Create Notice",
                        widget: Form(
                          key: _noticeFormKey,
                          child: Column(
                            children: [
                              TextFormField(
                                validator: (val) {
                                  if (val!.isEmpty) return "required";
                                },
                                controller: _noticeTitle,
                                decoration:
                                    InputDecoration(hintText: "notice title"),
                              ),
                              TextFormField(
                                validator: (val) {
                                  if (val!.isEmpty) return "required";
                                },
                                controller: _textData,
                                decoration: InputDecoration(
                                    hintText: "write notice here"),
                              ),
                            ],
                          ),
                        ));
                  },
                  child: Icon(Icons.add),
                );
              } else {
                return Container();
              }
            },
          ),
        );
      },
    );
  }
}
