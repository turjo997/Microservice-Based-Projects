import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../components/screen_size.dart';
import '../provider/course_info_trainer_trainee_provider.dart';

class CourseInfoTrainee extends StatefulWidget {
  const CourseInfoTrainee({Key? key}) : super(key: key);

  @override
  _CourseInfoTraineeState createState() => _CourseInfoTraineeState();
}

class _CourseInfoTraineeState extends State<CourseInfoTrainee> {

  @override
  Widget build(BuildContext context) {
    return Consumer<CourseInfoTrainerTraineeProvider>(
      builder: (context, courseInfoTrainerProvider, child){
        return Padding(
          padding: const EdgeInsets.all(18.0),
          child: Container(
            height: height(context),
            width: width(context) * .5,
            decoration: BoxDecoration(
              color: Colors.grey[200],
              borderRadius: BorderRadius.circular(12),
            ),
            child: FutureBuilder<List<dynamic>>(
              future: courseInfoTrainerProvider.getCoursesByBatchId(),
              builder: (context, snapshot) {
                if (!snapshot.hasData) {
                  return CircularProgressIndicator();
                } else {
                  return SingleChildScrollView(
                    scrollDirection: Axis.vertical,
                    child: SingleChildScrollView(
                      scrollDirection: Axis.horizontal,
                      child: DataTable(
                        dataRowMaxHeight: 80,
                        headingRowHeight: 70,
                        headingTextStyle: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 16,
                        ),
                        columns: const [
                          DataColumn(
                            label: Text('Course Name'),
                          ),
                          DataColumn(
                            label: Text('Start Date'),
                          ),
                          DataColumn(
                            label: Text('End Date'),
                          ),
                        ],
                        rows: snapshot.data!.map((course) {
                          return DataRow(cells: [
                            DataCell(Text(course["courseName"])),
                            DataCell(Text(course["startDate"])),
                            DataCell(Text(course["endDate"])),
                          ]);
                        }).toList(),
                      ),
                    ),
                  );
                }
              },
            ),
          ),
        );
      },
    );













    //—--------------------------------------------------





    // ;
  }
}
