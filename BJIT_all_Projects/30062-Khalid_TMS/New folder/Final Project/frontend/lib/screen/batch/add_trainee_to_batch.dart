import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:universal_html/html.dart' as html;
import '../../components/screen_size.dart';
import '../../provider/add_trainee_to_batch_provider.dart';

class AddTraineeToBatch extends StatelessWidget {
  const AddTraineeToBatch({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Consumer<AddTraineeToBatchProvider>(builder: (context,addTraineeToBatchProvider,child){
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
            future: addTraineeToBatchProvider.getTrainees(),
            builder: (context, snapshot) {
              if (!snapshot.hasData) {
                print("null");
                return CircularProgressIndicator();
              } else {
                return SingleChildScrollView(
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
                        label: Text('Email'),
                      ),
                      DataColumn(
                        label: Text('Educational Institute'),
                      ),
                      DataColumn(
                        label: Text('Add'),
                      ),
                    ],
                    rows: snapshot.data!.map((trainee) {
                      return DataRow(cells: [
                        DataCell(Text(trainee["fullName"])),
                        DataCell(Text(trainee["email"])),
                        DataCell(Text(trainee["educationalInstitute"])),
                        DataCell(
                          IconButton(
                            onPressed: () {
                              //addTraineeToBatchProvider.addToSelectedTrainees(trainee["traineeId"]);
                              var batchInfo = {
                                "traineeId": trainee["traineeId"],
                                "batchId": int.parse(getBatchIdInLocalStorage()!),
                              };
                              print(int.parse(getBatchIdInLocalStorage()!));
                              addTraineeToBatchProvider.postTraineeToBatch(batchInfo);
                            },
                            icon: Icon(Icons.add_box),
                          ),
                        ),
                      ]);
                    }).toList(),
                  ),
                );
              }
            },
          ),
        ),
      );
    });
  }
  String? getBatchIdInLocalStorage() {
    final storage = html.window.localStorage;
    return storage['batchId'];
  }
}