import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:training_management_system/provider/batches_of_trainer_provider.dart';

import '../components/color.dart';
import '../components/screen_size.dart';
import '../menu/trainer_menu_item.dart';

class BatchesOfTrainer extends StatefulWidget {
  final void Function(TrainerMenuItem) onMenuItemSelected;


   BatchesOfTrainer({Key? key,
     required  this.onMenuItemSelected,
   }) : super(key: key);

  @override
  State<BatchesOfTrainer> createState() => _BatchesOfTrainerState();
}

class _BatchesOfTrainerState extends State<BatchesOfTrainer> {
  @override
  Widget build(BuildContext context) {
    return  Consumer<BatchesOfTrainerProvider>(
        builder:(context, batchesOfTrainerProvider, child){
          return Column(
            children: [
              Expanded(
                  flex: 1,
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(0, 15, 0, 0),
                    child: Text("Batch List",
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
                  padding: EdgeInsets.all(50),
                  child: FutureBuilder<List<dynamic>>(
                    future: batchesOfTrainerProvider.getBatchListByTrainerId(),
                    builder: (context, snapshot) {
                      if (snapshot.hasData) {

                        return GridView.builder(
                          gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                            crossAxisCount: 3,
                            crossAxisSpacing: 16,
                            mainAxisSpacing: 16,
                          ),

                          itemCount: snapshot.data!.length,
                          itemBuilder: (context, index) {
                            var batchData = snapshot.data![index];
                            var batchId = batchData["batchId"].toString();
                            var batchName = batchData["batchName"];
                            var startDate = batchData["startDate"];
                            var endDate = batchData["endDate"];

                            return Card(
                              elevation: 4,
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10),
                              ),

                              child: InkWell(
                                onTap: () {
                                  String batchId = (snapshot.data![index]['batchId']).toString();
                                  batchesOfTrainerProvider.saveBatchIdInLocalStorage(batchId);
                                  widget.onMenuItemSelected(TrainerMenuItem.batchInfo);
                                },

                                child: Container(
                                  padding: EdgeInsets.all(16),
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                      Text(
                                        batchName,
                                        style: TextStyle(
                                          fontSize: 20,
                                          fontWeight: FontWeight.bold,
                                        ),
                                      ),
                                      SizedBox(height: 8),
                                      Text(
                                        "Batch ID: $batchId",
                                        style: TextStyle(
                                          fontSize: 16,
                                        ),
                                      ),
                                      SizedBox(height: 8),
                                      Text(
                                        "Start Date: $startDate",
                                        style: TextStyle(
                                          fontSize: 14,
                                          color: Colors.grey,
                                        ),
                                      ),
                                      SizedBox(height: 8),
                                      Text(
                                        "End Date: $endDate",
                                        style: TextStyle(
                                          fontSize: 14,
                                          color: Colors.grey,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            );
                          },
                        );
                      } else if (snapshot.hasError) {
                        return Text("Error: ${snapshot.error}");
                      }

                      return const CircularProgressIndicator();
                    },
                  ),
                ),
              ),
            ],
          );

        }
    );
  }
}
