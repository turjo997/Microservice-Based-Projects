// import 'dart:convert';
// import 'package:animated_text_kit/animated_text_kit.dart';
// import 'package:flutter/cupertino.dart';
// import 'package:flutter/material.dart';
// import 'package:intl/intl.dart';
// import 'package:provider/provider.dart';
// import 'package:quickalert/models/quickalert_type.dart';
// import 'package:quickalert/widgets/quickalert_dialog.dart';
// import 'package:training_management_system/components/batch_info_cards.dart';
// import 'package:training_management_system/provider/admin_batch_info_provider.dart';
// import 'package:http/http.dart' as http;
// import '../../components/box_decorations.dart';
// import '../../components/color.dart';
// import '../../components/my_app_bar.dart';
// import '../../components/screen_size.dart';
// import '../../components/text_style.dart';
// import "package:universal_html/html.dart" as html;
//
// import 'batch_information.dart';
//
//
// class AdminBatchInfo extends StatefulWidget {
//   const AdminBatchInfo({Key? key}) : super(key: key);
//
//   @override
//   _AdminBatchInfoState createState() => _AdminBatchInfoState();
// }
//
// class _AdminBatchInfoState extends State<AdminBatchInfo> {
//
//   final _courseFormKey = GlobalKey<FormState>();
//   final _courseName = TextEditingController();
//   final _description = TextEditingController();
//   final _startDateCourseController = TextEditingController();
//   final _endDateCourseController = TextEditingController();
//
//   CardsOfBatchInfo cardsOfBatchInfo = CardsOfBatchInfo();
//
//   Future<void> _selectStartDate(BuildContext context) async {
//     DateTime? pickedDate = await showDatePicker(
//       context: context,
//       initialDate: DateTime.now(),
//       firstDate: DateTime(2000),
//       lastDate: DateTime(2101),
//     );
//
//     if (pickedDate != null && pickedDate != DateTime.now()) {
//       // Format the selected date
//       String formattedDate = DateFormat('yyyy-MM-dd').format(pickedDate);
//       print('Selected date: $formattedDate');
//
//       // Update the text controller with the selected date
//       _startDateCourseController.text = formattedDate;
//     }
//   }
//
//   Future<void> _selectEndDate(BuildContext context) async {
//     DateTime? pickedDate = await showDatePicker(
//       context: context,
//       initialDate: DateTime.now(),
//       firstDate: DateTime(2000),
//       lastDate: DateTime(2101),
//     );
//
//     if (pickedDate != null && pickedDate != DateTime.now()) {
//       // Format the selected date
//       String formattedDate = DateFormat('yyyy-MM-dd').format(pickedDate);
//       print('Selected end date: $formattedDate');
//
//       // Update the text controller with the selected date
//       _endDateCourseController.text = formattedDate;
//     }
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return Consumer<AdminBatchInfoProvider>(
//       builder: (context, adminBatchInfoProvider, child) {   //????
//         return Scaffold(
//           appBar: MyAppBar(
//             title: 'TMS: Batch Info',
//           ),
//           body: SingleChildScrollView(
//             child: Column(
//               children: [
//                 Container(
//                   width: width(context)* 1,
//                   height: height(context) * 1,
//                   child: Row(
//                     mainAxisAlignment: MainAxisAlignment.start,
//                     children: [
//                       Expanded(
//                         flex: 2,
//                         child: Container(
//                           child: Padding(
//                             padding: const EdgeInsets.symmetric(horizontal: 38.0),
//                             child: Container(
//                               height: height(context) * .80,
//                               decoration: box12Sidebar,
//                               //color: Colors.orangeAccent,
//                               child: Column(
//                                 mainAxisAlignment: MainAxisAlignment.center,
//                                 children: [
//                                   InkWell(
//                                     onTap: (){},
//                                     child: Padding(
//                                       padding: const EdgeInsets.all(8.0),
//                                       child: Container(
//                                         //decoration: box12Sidebar,
//                                         child: TextLiquidFill(
//                                           text: 'Profile',
//                                           waveColor: sweetYellow,
//                                           boxBackgroundColor: Colors.black,
//                                           textStyle: black20,
//                                           boxHeight: 70,
//                                         ),
//                                       ),
//                                     ),
//                                   ),
//                                   InkWell(
//                                     onTap: (){
//                                       //Navigator.pop(context);
//                                       Navigator.pushNamed(context, "AdminDashboard");
//                                     },
//                                     child: Padding(
//                                       padding: const EdgeInsets.all(8.0),
//                                       child: Container(
//                                         //decoration: box12Sidebar,
//                                         child: TextLiquidFill(
//                                           text: 'BATCH',
//                                           waveColor: sweetYellow,
//                                           boxBackgroundColor: Colors.black,
//                                           textStyle: black20,
//                                           boxHeight: 70,
//                                         ),
//                                       ),
//                                     ),
//                                   ),
//                                   InkWell(
//                                     onTap: (){
//                                       Navigator.pushNamed(context, "TraineeRegister");
//                                     },
//                                     child: Padding(
//                                       padding: const EdgeInsets.all(8.0),
//                                       child: Container(
//                                         child: TextLiquidFill(
//                                           text: 'CREATE ACCOUNT',
//                                           waveColor: sweetYellow,
//                                           boxBackgroundColor: Colors.black,
//                                           textStyle: black20,
//                                           boxHeight: 70,
//                                         ),
//                                       ),
//                                     ),
//                                   ),
//                                   InkWell(
//                                     onTap: (){},
//                                     child: Padding(
//                                       padding: const EdgeInsets.all(8.0),
//                                       child: Container(
//                                         child: TextLiquidFill(
//                                           text: 'Logout',
//                                           waveColor: sweetYellow,
//                                           boxBackgroundColor: Colors.black,
//                                           textStyle: black20,
//                                           boxHeight: 70,
//                                         ),
//                                       ),
//                                     ),
//                                   ),
//
//                                 ],
//                               ),
//                             ),
//                           ),
//                         ),
//                       ),
//                       //body
//                       // Expanded(   // dashboard
//                       //   flex: 4,
//                       //   child: Container(
//                       //     child: BatchInformation(cardsOfBatchInfo: cardsOfBatchInfo, ),
//                       //   ),
//                       // ),
//                       Expanded(
//                         flex: 1,
//                         child: Container(
//                         ),
//                       ),
//                     ],
//                   ),
//                 ),
//               ],
//             ),
//           ),
//         );
//       },
//     );
//   }
//
//   void saveBatchIdInLocalStorage(String batchId) {
//     final storage = html.window.localStorage;
//     storage['batchId'] = batchId;
//   }
//   //
//   // Widget buildDashboardCard({required String title, required String subtitle}) {
//   //   return Padding(
//   //     padding: const EdgeInsets.fromLTRB(0, 0, 10, 8),
//   //     child: SizedBox(
//   //       width: width(context)* 0.18,
//   //       height: height(context)*0.25, // Set a fixed height for the card
//   //       child: Container(
//   //         decoration: box12,
//   //         child: Column(
//   //           mainAxisAlignment: MainAxisAlignment.center,
//   //           crossAxisAlignment: CrossAxisAlignment.center,
//   //           children: [
//   //             Text(
//   //               title,
//   //               style: TextStyle(fontSize: 26, fontWeight: FontWeight.bold),
//   //             ),
//   //             SizedBox(height: 10),
//   //             Text(
//   //               subtitle,
//   //               style: TextStyle(fontSize: 20),
//   //             ),
//   //             SizedBox(height: 15),
//   //             ElevatedButton(
//   //               onPressed: () {
//   //                 // Navigate to corresponding page on button press
//   //               },
//   //               child: Text('Go to $title'),
//   //             ),
//   //           ],
//   //         ),
//   //       ),
//   //     ),
//   //   );
//   // }
//
//   Future<void> postCourse(var courseInfo) async {
//
//     String? token = getTokenFromLocalStorage();
//     String? url = 'http://localhost:8090/course/create';
//
//     final headers = {
//       'Content-Type': 'application/json',
//       'Authorization': 'Bearer $token',
//     };
//
//     String? getBatchIdInLocalStorage() {
//       final storage = html.window.localStorage;
//       return storage['batchId'];
//     }
//     int tempBatchId =  int.parse(getBatchIdInLocalStorage()!);
//
//     final data = {
//       "courseName": courseInfo['courseName'],
//       "description": courseInfo['description'],
//       "startDate": courseInfo['startDate'],
//       "endDate": courseInfo['endDate'],
//       "batchId": tempBatchId,
//
//     };
//     final jsonBody = jsonEncode(data);
//     final response =
//     await http.post(Uri.parse(url), headers: headers, body: jsonBody);
//
//     if (response.statusCode == 200) {
//       print(response.body);
//       print('Data posted successfully');
//       Navigator.pop(context);
//     } else {
//       // Error occurred while posting data
//       print('Error occurred while posting data: ${response.body}');
//     }
//   }
//
//   String? getTokenFromLocalStorage() {
//     final storage = html.window.localStorage;
//     return storage['token'];
//   }
//
//   String dateFormatter(String date){
//     return DateFormat('dd MMMM, yyyy').format(DateTime.parse(date));
//
//   }
// }
//
//
