import 'package:flutter/material.dart';
import 'package:training_management_system/components/color.dart';

BoxDecoration box12 =  BoxDecoration(
  color: primary.withOpacity(.15),
    border: Border.all(width: 2,color: Colors.black54.withOpacity(.15)),
    borderRadius: BorderRadius.all(Radius.circular(12)),
);

BoxDecoration box12Sidebar =  BoxDecoration(
  border: Border.all(width: 5,color: sweetYellow),
  borderRadius: BorderRadius.all(Radius.circular(12)),
);


BoxDecoration box12orange =  BoxDecoration(
  color: Colors.yellow[400]?.withOpacity(.65),
  gradient: const LinearGradient(
      colors: [
        Colors.white,
        Colors.orangeAccent,
      ]

  ),

  border: Border.all(width: 2,color: Colors.black54.withOpacity(.15)),
  borderRadius: const BorderRadius.all(Radius.circular(12)),
);


BoxDecoration boxNoBorder =  BoxDecoration(
  color: Colors.grey[100],
  border: Border.all(width: 2,color: primary.withOpacity(.5)),
  borderRadius: BorderRadius.all(Radius.circular(25)),
);

BoxDecoration boxNoBorder2 =  BoxDecoration(
  color: Colors.blue[50],

  border: Border.all(width: 2,color: primary.withOpacity(.5)),
  borderRadius: BorderRadius.all(Radius.circular(25)),
);