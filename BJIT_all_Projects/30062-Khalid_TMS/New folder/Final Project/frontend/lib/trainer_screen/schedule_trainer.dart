import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../provider/schedule_provider.dart';

class ScheduleTrainer extends StatelessWidget {
  const ScheduleTrainer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Consumer<ScheduleProvider>(
      builder:(context, scheduleProvider, child){
        return Column(
          children: [

            Expanded(
              flex: 1,
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text("Schedule",
                    style: TextStyle(
                    fontSize: 30,
                    fontWeight: FontWeight.w900,
                  ),
                  ),
                )
            ),
            Expanded(
              flex: 9,
              child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Container(
                    child: FutureBuilder<List<dynamic>>(
                      future: scheduleProvider.getScheduleByBatchId(),
                      builder: (context, snapshot) {
                        if (snapshot.connectionState == ConnectionState.waiting) {
                          return Center(
                            child: CircularProgressIndicator(),
                          );
                        } else if (snapshot.hasError || !snapshot.hasData) {
                          return Center(
                            child: Text("No schedule found."),
                          );
                        } else {
                          List<dynamic> scheduleList = snapshot.data!;
                          return ListView.builder(
                            itemCount: scheduleList.length,
                            itemBuilder: (context, index) {
                              var scheduleItem = scheduleList[index];
                              String courseName = scheduleItem["courseName"];
                              String description = scheduleItem["description"];
                              String startDateStr = scheduleItem["startDate"];
                              String endDateStr = scheduleItem["endDate"];

                              // Helper function to parse date strings to DateTime objects
                              DateTime parseDate(String dateStr) {
                                return DateTime.parse(dateStr);
                              }

                              DateTime startDate = parseDate(startDateStr);
                              DateTime endDate = parseDate(endDateStr);
                              DateTime currentDate = DateTime.now();

                              bool isOngoing = currentDate.isAfter(startDate) && currentDate.isBefore(endDate);
                              bool hasEnded = currentDate.isAfter(endDate);

                              return Card(
                                color: hasEnded ? Colors.grey[300] : Colors.white,
                                child: Padding(
                                  padding: const EdgeInsets.all(16.0),
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text(
                                        courseName,
                                        style: TextStyle(
                                          fontWeight: FontWeight.bold,
                                          fontSize: 20,
                                        ),
                                      ),
                                      SizedBox(height: 8),
                                      Text(
                                        description,
                                        style: TextStyle(
                                          fontSize: 16,
                                          color: Colors.grey[600],
                                        ),
                                      ),
                                      SizedBox(height: 8),
                                      Row(
                                        children: [
                                          Text(
                                            "Start Date: ",
                                            style: TextStyle(
                                              fontSize: 14,
                                              color: hasEnded ? Colors.red : (isOngoing ? Colors.green : Colors.grey[600]),
                                            ),
                                          ),
                                          Text(
                                            startDateStr,
                                            style: TextStyle(
                                              fontSize: 14,
                                              color: hasEnded ? Colors.red : (isOngoing ? Colors.green : Colors.grey[600]),
                                            ),
                                          ),
                                        ],
                                      ),
                                      SizedBox(height: 8),
                                      Row(
                                        children: [
                                          Text(
                                            "End Date: ",
                                            style: TextStyle(
                                              fontSize: 14,
                                              color: hasEnded ? Colors.red : (isOngoing ? Colors.green : Colors.grey[600]),
                                            ),
                                          ),
                                          Text(
                                            endDateStr,
                                            style: TextStyle(
                                              fontSize: 14,
                                              color: hasEnded ? Colors.red : (isOngoing ? Colors.green : Colors.grey[600]),
                                            ),
                                          ),
                                        ],
                                      ),
                                    ],
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
            ),
          ],
        );



      }
    );
  }
}
