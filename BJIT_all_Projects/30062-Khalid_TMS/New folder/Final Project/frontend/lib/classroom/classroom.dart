import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import 'package:quickalert/models/quickalert_type.dart';
import 'package:quickalert/widgets/quickalert_dialog.dart';
import 'package:training_management_system/components/box_decorations.dart';
import 'package:training_management_system/components/screen_size.dart';
import 'package:training_management_system/provider/classroom_provider.dart';

import '../components/color.dart';

class ClassroomPage extends StatefulWidget {
  const ClassroomPage({Key? key}) : super(key: key);

  @override
  State<ClassroomPage> createState() => _ClassroomPageState();
}

class _ClassroomPageState extends State<ClassroomPage> {
  final commentData = TextEditingController();
  int a = 5;

  final _classRoomPostFormKey = GlobalKey<FormState>();
  final textData = TextEditingController();
  String formattedDate = DateFormat('yyyy-MM-dd').format(DateTime.now());

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<ClassRoomProvider>(
      builder: (context, classRoomProvider, child) {
        return Padding(
          padding: const EdgeInsets.all(8.0),
          child: Container(
            height: height(context),
            child: SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      "Classroom",
                      style:
                          TextStyle(fontWeight: FontWeight.w700, fontSize: 30),
                    ),
                  ),

                  ///create button based on role.........
                  classRoomProvider.getRoleFromLocalStorage() == "TRAINER"
                      ? Padding(
                          padding: EdgeInsets.all(8.0),
                          child: ElevatedButton(
                            onPressed: () {
                              QuickAlert.show(
                                  onConfirmBtnTap: () {
                                    if (_classRoomPostFormKey.currentState!
                                        .validate()) {
                                      classRoomProvider.createPost({
                                        'textData': textData.text,
                                        'postDate': formattedDate,
                                      }, context);
                                    }
                                  },
                                  context: context,
                                  type: QuickAlertType.info,
                                  confirmBtnColor: sweetYellow,
                                  confirmBtnText: "Create",
                                  title: "Create Post",
                                  widget: Form(
                                    key: _classRoomPostFormKey,
                                    child: Column(
                                      children: [
                                        TextFormField(
                                          validator: (val) {
                                            if (val!.isEmpty) {
                                              return "Required";
                                            }
                                          },
                                          controller: textData,
                                          decoration: const InputDecoration(
                                              hintText:
                                                  "Write Something Here..."),
                                        ),
                                        Container(
                                          height: 100,
                                          width: 500,
                                          child: Row(
                                            children: [
                                              const SizedBox(
                                                width: 100,
                                              ),
                                              Expanded(
                                                flex: 1,
                                                child: ElevatedButton(
                                                  onPressed: () {
                                                    classRoomProvider
                                                        .pickFile(context);
                                                  },
                                                  child: classRoomProvider
                                                              .assignment ==
                                                          null
                                                      ? const Text(
                                                          "Select File")
                                                      : Icon(
                                                          Icons
                                                              .check_box_rounded,
                                                          color: Colors.grey,
                                                        ),
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                        // TextFormField(),
                                        //TextFormField(),
                                      ],
                                    ),
                                  ));
                            },
                            child: Text("Create Post"),
                          ),
                        )
                      : const SizedBox(),

                  Container(
                    height: height(context) * .9,
                    child: FutureBuilder<List<dynamic>>(
                      future: classRoomProvider.getAllPosts(),
                      builder: (context, snapshot) {
                        if (snapshot.hasData) {
                          List? allPosts = snapshot!.data;
                          return ListView.builder(
                            itemCount: allPosts!.length,
                            itemBuilder: (context, index) {
                              return Padding(
                                padding: const EdgeInsets.all(6.0),
                                child: Container(
                                  decoration: boxNoBorder,
                                  width: width(context) * .4,
                                  height: height(context) * .5,
                                  child: Row(
                                    children: [
                                      Expanded(
                                        flex: 1,
                                        child: Column(
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            Padding(
                                              padding:
                                                  const EdgeInsets.all(8.0),
                                              child: Text(
                                                allPosts[index]["trainerName"],
                                                textAlign: TextAlign.start,
                                                style: TextStyle(
                                                  fontWeight: FontWeight.w700,
                                                  fontSize: 30,
                                                ),
                                              ),
                                            ),
                                            Padding(
                                              padding:
                                                  const EdgeInsets.all(6.0),
                                              child: Text(
                                                allPosts[index]["textData"],
                                                textAlign: TextAlign.start,
                                              ),
                                            ),
                                            SizedBox(
                                              height: height(context) * .20,
                                            ),
                                            if (allPosts[index]["filePath"] !=
                                                null)
                                              Padding(
                                                padding:
                                                    const EdgeInsets.all(6),
                                                child: Row(
                                                  children: [
                                                    Text(
                                                      "Attatchments",
                                                      style: TextStyle(
                                                          fontWeight:
                                                              FontWeight.w600),
                                                    ),
                                                    SizedBox(
                                                      width: 8,
                                                    ),
                                                    IconButton(
                                                        onPressed: () {
                                                          classRoomProvider
                                                              .downloadFile(
                                                                  "http://localhost:8090/classroom/download/${allPosts[index]["postId"]}");
                                                        },
                                                        icon: Icon(
                                                            Icons.attachment)),
                                                  ],
                                                ),
                                              ),
                                          ],
                                        ),
                                      ),
                                      Expanded(
                                        flex: 1,
                                        child: Column(
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          children: [
                                            InkWell(
                                              onTap: () {
                                                showDialog(
                                                    context: context,
                                                    builder: (context) {
                                                      return AlertDialog(
                                                          content:
                                                              SingleChildScrollView(
                                                        child: Column(
                                                          children: [
                                                            Container(
                                                              height: height(context) * .7,
                                                              width: width(context) *.5,
                                                              color: Colors.white60,
                                                              child: ListView
                                                                  .builder(
                                                                      itemCount: allPosts[index]["comments"].length,
                                                                      itemBuilder: (context, commentIndex) {
                                                                        var comment = allPosts[index]["comments"][commentIndex];
                                                                        return Column(
                                                                          crossAxisAlignment:
                                                                              CrossAxisAlignment.start,
                                                                          children: [
                                                                            Padding(
                                                                              padding: const EdgeInsets.all(8.0),
                                                                              child: Row(
                                                                                children: [
                                                                                  Icon(Icons.supervised_user_circle),
                                                                                  Text(comment["name"]),
                                                                                ],
                                                                              ),
                                                                            ),
                                                                            Padding(
                                                                              padding: const EdgeInsets.all(8.0),
                                                                              child: Text(comment["commentData"]),
                                                                            ),
                                                                            Padding(
                                                                              padding: const EdgeInsets.all(8.0),
                                                                              child: Divider(),
                                                                            ),
                                                                          ],
                                                                        );
                                                                      }),
                                                            ),
                                                            Container(
                                                              height: height(
                                                                      context) *
                                                                  .09,
                                                              color: Colors
                                                                  .white60,
                                                              child: Padding(
                                                                padding:
                                                                    const EdgeInsets
                                                                            .all(
                                                                        8.0),
                                                                child:
                                                                    TextFormField(
                                                                  controller:
                                                                      commentData,
                                                                  decoration:
                                                                      InputDecoration(
                                                                          suffix:
                                                                              TextButton(
                                                                            onPressed:
                                                                                () {
                                                                              if (commentData.text != "") {
                                                                                var commentDataMap = {
                                                                                  "commentData": commentData.text,
                                                                                  "postId": allPosts[index]["postId"],
                                                                                };
                                                                                classRoomProvider.createComment(commentDataMap, context);
                                                                                commentData.text = "";
                                                                                Navigator.pop(context);
                                                                              }
                                                                            },
                                                                            child:
                                                                                Text("POST"),
                                                                          ),
                                                                          hintText:
                                                                              "Type Your Comment Here...",
                                                                          border:
                                                                              OutlineInputBorder(
                                                                            borderRadius:
                                                                                BorderRadius.all(Radius.circular(20)),
                                                                          )),
                                                                ),
                                                              ),
                                                            ),
                                                          ],
                                                        ),
                                                      ));
                                                    });
                                              },
                                              child: Padding(
                                                padding:
                                                    const EdgeInsets.all(8.0),
                                                child: Container(
                                                  height: height(context) * .30,
                                                  width: width(context),
                                                  decoration: boxNoBorder2,
                                                  child: ListView.builder(
                                                      itemCount: allPosts[index]
                                                              ["comments"]
                                                          .length,
                                                      itemBuilder: (context,
                                                          commentIndex) {
                                                        var comment =
                                                            allPosts[index]
                                                                    ["comments"]
                                                                [commentIndex];
                                                        return Row(
                                                          crossAxisAlignment:
                                                              CrossAxisAlignment
                                                                  .start,
                                                          children: [
                                                            Padding(
                                                              padding:
                                                                  const EdgeInsets
                                                                      .all(8.0),
                                                              child: Row(
                                                                children: [
                                                                  Icon(Icons
                                                                      .supervised_user_circle),
                                                                  Text(
                                                                    comment[
                                                                        "name"],
                                                                    style: TextStyle(
                                                                        fontWeight:
                                                                            FontWeight.w600),
                                                                  ),
                                                                ],
                                                              ),
                                                            ),
                                                            Padding(
                                                              padding:
                                                                  const EdgeInsets
                                                                      .all(8.0),
                                                              child: Text(
                                                                comment[
                                                                    "commentData"],
                                                                style: TextStyle(
                                                                    color: Colors
                                                                        .black54),
                                                              ),
                                                            ),
                                                            Padding(
                                                              padding:
                                                                  const EdgeInsets
                                                                      .all(8.0),
                                                              child: Divider(),
                                                            ),
                                                          ],
                                                        );
                                                      }),
                                                ),
                                              ),
                                            ),
                                            Container(
                                              height: height(context) * .09,
                                              child: Padding(
                                                padding:
                                                    const EdgeInsets.all(8.0),
                                                child: TextFormField(
                                                  controller: commentData,
                                                  decoration: InputDecoration(
                                                      fillColor:
                                                          Colors.deepPurple,
                                                      hoverColor: Colors.green,
                                                      focusColor:
                                                          Colors.blueAccent,
                                                      suffix: TextButton(
                                                        onPressed: () {
                                                          if (commentData
                                                                  .text !=
                                                              "") {
                                                            var commentDataMap =
                                                                {
                                                              "commentData":
                                                                  commentData
                                                                      .text,
                                                              "postId":
                                                                  allPosts[
                                                                          index]
                                                                      [
                                                                      "postId"],
                                                            };
                                                            classRoomProvider
                                                                .createComment(
                                                                    commentDataMap,
                                                                    context);
                                                            commentData.text =
                                                                "";
                                                          }
                                                        },
                                                        child: Text("POST"),
                                                      ),
                                                      hintText:
                                                          "Type Your Comment Here...",
                                                      border:
                                                          OutlineInputBorder(
                                                        borderRadius:
                                                            BorderRadius.all(
                                                                Radius.circular(
                                                                    20)),
                                                      )),
                                                ),
                                              ),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              );
                            },
                          );
                        }
                        if (snapshot.hasError) {
                          return Text("snapshot error ${snapshot.error}");
                        }
                        return Text("something went wrong");
                      },
                    ),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}
