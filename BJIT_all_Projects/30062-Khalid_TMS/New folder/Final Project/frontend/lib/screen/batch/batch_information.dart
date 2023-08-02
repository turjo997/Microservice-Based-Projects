import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import 'package:quickalert/models/quickalert_type.dart';
import 'package:quickalert/widgets/quickalert_dialog.dart';
import '../../components/batch_info_cards.dart';
import '../../components/color.dart';
import '../../menu/menu_item.dart';
import '../../provider/admin_batch_info_provider.dart';import 'course_info_of_batch.dart';
import 'course_info_of_batch.dart';



class BatchInformation extends StatelessWidget {

  final void Function(MenuItem) onMenuItemSelected;


   BatchInformation({
    super.key,
    required this.cardsOfBatchInfo,
     required this.onMenuItemSelected,
  });

  final _courseFormKey = GlobalKey<FormState>();
  final _courseName = TextEditingController();
  final _description = TextEditingController();
  final _startDateCourseController = TextEditingController();
  final _endDateCourseController = TextEditingController();

   Future<void> _selectStartDate(BuildContext context) async {
     DateTime? pickedDate = await showDatePicker(
       context: context,
       initialDate: DateTime.now(),
       firstDate: DateTime(2000),
       lastDate: DateTime(2101),
     );

     if (pickedDate != null && pickedDate != DateTime.now()) {
       // Format the selected date
       String formattedDate = DateFormat('yyyy-MM-dd').format(pickedDate);
       print('Selected date: $formattedDate');

       // Update the text controller with the selected date
       _startDateCourseController.text = formattedDate;
     }
   }

   Future<void> _selectEndDate(BuildContext context) async {
     DateTime? pickedDate = await showDatePicker(
       context: context,
       initialDate: DateTime.now(),
       firstDate: DateTime(2000),
       lastDate: DateTime(2101),
     );

     if (pickedDate != null && pickedDate != DateTime.now()) {
       // Format the selected date
       String formattedDate = DateFormat('yyyy-MM-dd').format(pickedDate);
       print('Selected end date: $formattedDate');

       // Update the text controller with the selected date
       _endDateCourseController.text = formattedDate;
     }
   }

  final CardsOfBatchInfo cardsOfBatchInfo;

  @override
  Widget build(BuildContext context) {
    return Consumer<AdminBatchInfoProvider>(builder: (context,adminBatchInfoProvider,child){
      return Padding(
        padding: const EdgeInsets.fromLTRB(50, 50, 50, 50),
        child: Container(
          child: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  InkWell(
                    onTap: () {
                      onMenuItemSelected(MenuItem.AddTrainerToBatch);
                    },
                    child: cardsOfBatchInfo.cards(
                      context, 'Trainer Assign',
                      'Assign Trainer in Batch',
                    ),
                  ),
                  InkWell(
                    onTap: () {
                      onMenuItemSelected(MenuItem.AddTraineeToBatch);
                    },
                    child: cardsOfBatchInfo.cards(
                      context, 'Assign Trainee',
                      'Assign Trainee in Batch',
                    ),
                  ),
                  InkWell(
                    onTap: () {
                      QuickAlert.show(
                          onConfirmBtnTap: (){
                            if(_courseFormKey.currentState!.validate()){

                              var courseInfo = {
                                'courseName': _courseName.text,
                                'description': _description.text,
                                'startDate': _startDateCourseController.text,
                                'endDate': _endDateCourseController.text,
                              };

                              adminBatchInfoProvider.postCourse(courseInfo, context);
                            }
                          },
                          context: context,
                          type: QuickAlertType.info,
                          confirmBtnColor: sweetYellow,
                          confirmBtnText: "Create",
                          title: "Create Course",

                          widget: Form(
                            key: _courseFormKey,
                            child: Column(
                              children: [

                                TextFormField(
                                  validator: (val){
                                    if(val!.isEmpty) return "value des nai oak thu";
                                  },
                                  controller: _courseName,
                                  decoration: InputDecoration(hintText: "Course Name"),
                                ),

                                TextFormField(
                                  validator: (val){
                                    if(val!.isEmpty) return "value des nai oak thu";
                                  },
                                  controller: _description,
                                  decoration: InputDecoration(hintText: "Description"),
                                ),

                                Container(
                                  height: 100,
                                  width: 500,
                                  //color: Colors.red,
                                  child: Row(
                                    children: [
                                      Expanded(
                                        flex: 1,
                                        child: TextFormField(
                                          validator: (val){
                                            if(val!.isEmpty) return "value des nai oak thu";
                                          },
                                          controller: _startDateCourseController,
                                          readOnly: true,
                                          // Make the field read-only to prevent manual input
                                          onTap: () => _selectStartDate(context),
                                          decoration: const InputDecoration(
                                            labelText: 'Start Date',
                                            hintText: 'Select a date',
                                            suffixIcon: Icon(Icons.calendar_today),
                                            border: OutlineInputBorder(),
                                          ),
                                        ),
                                      ),
                                      const SizedBox(
                                        width: 100,
                                      ),
                                      Expanded(
                                        flex: 1,
                                        child: TextFormField(
                                          validator: (val){
                                            if(val!.isEmpty) return "value des nai oak thu";
                                          },
                                          controller: _endDateCourseController,
                                          readOnly: true,
                                          onTap: () => _selectEndDate(context),
                                          decoration: const InputDecoration(
                                            labelText: 'End Date',
                                            hintText: 'Select an end date',
                                            suffixIcon: Icon(Icons.calendar_today),
                                            border: OutlineInputBorder(),
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
                    child: cardsOfBatchInfo.cards(
                      context, 'Create Course',
                      'Create Assignment',
                    ),
                  ),
                ],
              ),
              SizedBox(height: 20),
              Row(
                mainAxisAlignment: MainAxisAlignment
                    .start,
                children: [
                  InkWell(
                    onTap: () {
                      onMenuItemSelected(MenuItem.BatchDetailsPage);
                    },
                    child: cardsOfBatchInfo.cards(
                      context,'Batch Details',
                      'Check and Edit Batch',
                    ),
                  ),
                  InkWell(
                    onTap: () {
                      onMenuItemSelected(MenuItem.CourseInfoPage);
                    },
                    child: cardsOfBatchInfo.cards(
                      context ,'Courses',
                      'View and Update courses',
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      );
    });
  }
}