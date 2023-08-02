import 'dart:js_interop';

import 'package:flutter/material.dart';
import 'package:front/controller/app_controller.dart';
import 'package:front/controller/auth_controller.dart';
import 'package:front/pages/widgets/evaluation_final_project_table.dart';
import 'package:front/pages/widgets/evaluation_task_table.dart';
import 'package:provider/provider.dart';

import '../models/batch.dart';
import '../models/task_types.dart';

class EvaluationPage extends StatefulWidget {
  const EvaluationPage({Key? key}) : super(key: key);

  @override
  EvaluationPageState createState() => EvaluationPageState();
}

class EvaluationPageState extends State<EvaluationPage> {
  String? selectedBatchName;
  String? selectedTaskType;
  String? selectedTaskName;
  int? selectedTaskId;

  List<String> taskTypes = TaskType.values.map((e) => e.name).toList();
  // Initialize empty list for task names.

  @override
  void initState() {
    super.initState();
    context.read<AppController>().getData();
  }

  int? getBatchId(List<Batch> batchList, String batchName) {
    for (var batch in batchList) {
      if (batch.batchName == batchName) {
        return batch.id!;
      }
    }
    return null;
  }

  void getTask() {
    var id = getBatchId(context.read<AppController>().batches, selectedBatchName!);
    if (selectedTaskType != null && id != null) {
      context
          .read<AppController>()
          .getTaskMap(taskType: selectedTaskType, batchId: id);
    }
  }

  @override
  Widget build(BuildContext context) {
    final appController = context.watch<AppController>();

    return ClipRRect(
      borderRadius: BorderRadius.circular(12),
      child: Scaffold(
        body: appController.isLoading
            ? const Center(child: CircularProgressIndicator())
            : Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(
                        horizontal: 50, vertical: 15),
                    child: Row(
                      children: [
                        Expanded(
                          child: Stack(
                            alignment: Alignment.centerRight,
                            children: [
                              DropdownButtonFormField<String>(
                                value: selectedBatchName,
                                hint: const Text("Select Batch"),
                                items: appController.batches.map((batch) {
                                  return DropdownMenuItem<String>(
                                    value: batch.batchName,
                                    child: Text(batch.batchName ?? ""),
                                  );
                                }).toList(),
                                onChanged: (String? newValue) {
                                  setState(() {
                                    selectedBatchName = newValue;
                                    selectedTaskType = null;
                                    selectedTaskId = null;
                                    selectedTaskName =null;

                                  });
                                  getTask();
                                  // Call a method to fetch task types based on selectedBatchId.
                                  // For example: context.read<AppController>().getTaskTypes(batchId: selectedBatchId);
                                },
                              ),
                              if (selectedBatchName != null)
                                Padding(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 20),
                                  child: IconButton(
                                    onPressed: () {
                                      setState(() {
                                        selectedBatchName = null;
                                        selectedTaskType = null;
                                        selectedTaskName = null;
                                        selectedTaskId = null;
                                      });

                                      // Clear the selected batch and reset the task types and task names.

                                      appController.taskMap = {};
                                    },
                                    icon: Icon(Icons.clear),
                                  ),
                                ),
                            ],
                          ),
                        ),
                        const SizedBox(
                          width: 20,
                        ),
                        Expanded(
                          child: Stack(
                            alignment: Alignment.centerRight,
                            children: [
                              DropdownButtonFormField<String>(
                                value: selectedTaskType,
                                hint: const Text("Select Task Type"),
                                items: taskTypes.map((String taskType) {
                                  return DropdownMenuItem<String>(
                                    value: taskType,
                                    child: Text(taskType),
                                  );
                                }).toList(),
                                onChanged: (String? newValue) {
                                  setState(() {
                                    selectedTaskType = newValue;
                                    selectedTaskName =null;
                                    selectedTaskId =null;
                                  });
                                  getTask();
                                  // Call a method to fetch task names based on selectedBatchId and selectedTaskType.
                                  // For example: context.read<AppController>().getTaskNames(batchId: selectedBatchId, taskType: selectedTaskType);
                                },
                              ),
                              if (selectedTaskType != null)
                                Padding(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 20),
                                  child: IconButton(
                                    onPressed: () {
                                      setState(() {
                                        selectedTaskType = null;
                                        selectedTaskName = null;
                                        selectedTaskId = null;

                                      });
                                      // Clear the selected task type and reset the task names.
                                    },
                                    icon: Icon(Icons.clear),
                                  ),
                                ),
                            ],
                          ),
                        ),
                        const SizedBox(
                          width: 20,
                        ),
                        Expanded(
                          child: Stack(
                            alignment: Alignment.centerRight,
                            children: [
                              DropdownButtonFormField<String>(
                                value: selectedTaskName,
                                hint: const Text("Select Task Name"),
                                items: appController.taskMap.isEmpty
                                    ? [
                                        const DropdownMenuItem<String>(
                                          value: null,
                                          child: Text(
                                              "Please select batch and task type"),
                                        ),
                                      ]
                                    : appController.taskMap.entries
                                        .map((entry) {
                                        return DropdownMenuItem<String>(
                                          value: entry.key?.toString(),
                                          child: Text(entry.key!),
                                        );
                                      }).toList(),
                                onChanged: (String? newValue) async{
                                  await context.read<AppController>().getEvaluation(taskType:selectedTaskType,batchId: getBatchId(appController.batches, selectedBatchName!));
                                  setState(() {
                                    selectedTaskId = appController.taskMap[newValue];
                                    selectedTaskName = newValue;
                                  });

                                  // After selecting the task name, you can call a method or perform any other actions you need.
                                  // For example: context.read<AppController>().getTaskDetails(taskName: selectedTaskName);
                                },
                              ),
                              if (selectedTaskName != null)
                                Padding(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 20),
                                  child: IconButton(
                                    onPressed: () {
                                      setState(() {
                                        selectedTaskName = null;
                                        selectedTaskId = null;
                                      });
                                    },
                                    icon: const Icon(Icons.clear),
                                  ),
                                ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(
                    width: 20,
                  ),
                  Expanded(
                          child: Padding(
                            padding: const EdgeInsets.all(18),
                            child: (selectedTaskId==null||selectedTaskType==null||selectedBatchName==null)
                                ? const Center(
                                    child: Text("No available evaluations"))
                                : _buildChildWidget( selectedTaskId!,
                                    selectedTaskType!, getBatchId(appController.batches, selectedBatchName!)!),
                          ),
                        ),
                ],
              ),
      ),
    );
  }

  Widget _buildChildWidget(int taskId,String selectedTaskType, int batchId) {
    var id =context.read<AuthController>().userId!;
    switch (selectedTaskType) {
      case 'FINAL_PROJECT':
        return FutureBuilder(
          future: context.read<AppController>().getBatch(batchId),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else {
              if (snapshot.hasError) {
                return const Center(child: Text("Error occurred"));
              } else if(snapshot.data.isNull){
                return const Center(child: Text("Not Batch info found for evaluation"));
              }
              else{
                return FinalProjectEvaluationTable(
                  evaluationList: context.read<AppController>().evaluationFinalsProject,
                  taskId: selectedTaskId!,
                  taskType: selectedTaskType,
                  batch: snapshot.data!, userId:id,);
              }
            }
          },
        );
      default:
        return FutureBuilder(
          future: context.read<AppController>().getBatch(batchId),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else {
              if (snapshot.hasError) {
                return const Center(child: Text("Error occurred"));
              } else if(snapshot.data.isNull){
                return const Center(child: Text("Not Batch info found for evaluation"));
              }
              else{
                return TaskEvaluationTable(
                    evaluationList: context.read<AppController>().filterTask(taskId),
                    taskId: selectedTaskId!,
                    taskType: selectedTaskType,
                    batch: snapshot.data!, userId:id,);
              }
            }
          },
        );

    }
  }
}
