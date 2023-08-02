import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:universal_html/html.dart' as html;
import 'package:training_management_system/components/screen_size.dart';
import 'package:training_management_system/provider/add_trainer_to_batch_provider.dart';

class AddTrainerToBatch extends StatelessWidget {
  const AddTrainerToBatch({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Consumer<AddTrainerToBatchProvider>(builder: (context,addTrainerToBatchProvider,child){
      return Padding(
        padding: const EdgeInsets.all(18.0),
        child: Container(
          height: height(context),
          width: width(context) * .5,
          decoration: BoxDecoration(
            color: Colors.grey[200], // Set the background color of the table
            borderRadius: BorderRadius.circular(12),
          ),

          child: FutureBuilder<List<dynamic>>(
            future: addTrainerToBatchProvider.getTrainers(),
            builder: (context, snapshot) {
              if (!snapshot.hasData) {
                return CircularProgressIndicator();
              } else {
                return SingleChildScrollView(
                  scrollDirection: Axis.vertical, // Enable vertical scrolling
                  child: SingleChildScrollView(
                    scrollDirection: Axis.horizontal, // Enable horizontal scrolling
                    child: DataTable(
                      dataRowHeight: 60,
                      headingRowHeight: 70,
                      headingTextStyle: TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                      columns: [
                        DataColumn(
                          label: Text('Name'),
                        ),
                        DataColumn(
                          label: Text('Designation'),
                        ),
                        DataColumn(
                          label: Text('Email'),
                        ),
                        DataColumn(
                          label: Text('Add'),
                        ),
                      ],
                      rows: snapshot.data!.map((trainer) {
                        return DataRow(cells: [
                          DataCell(Text(trainer["fullName"])),
                          DataCell(Text(trainer["designation"])),
                          DataCell(Text(trainer["email"])),
                          DataCell(
                            IconButton(
                              onPressed: () {
                                //addTrainerToBatchProvider.addToSelectedTrainers(trainer["trainerId"]);
                                var batchInfo = {
                                  "trainerId": trainer["trainerId"],
                                  "batchId": int.parse(getBatchIdInLocalStorage()!),
                                };
                                print(int.parse(getBatchIdInLocalStorage()!));
                                addTrainerToBatchProvider.postTrainerToBatch(batchInfo);
                              },
                              icon: Icon(Icons.add_box),
                            ),
                          ),
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
    }
    );
  }
  String? getBatchIdInLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }
}