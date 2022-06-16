import 'package:flutter/material.dart';
import 'package:shimmer/shimmer.dart';

class ShimmerVerticalList extends StatelessWidget {
  const ShimmerVerticalList({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    int time = 800;
    return SafeArea(
      child: SizedBox(
        width: MediaQuery.of(context).size.width,
        child: ListView.builder(
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          itemCount: 6,
          itemBuilder: (BuildContext context, int index) {
            time = 800;
            return Padding(
                padding: const EdgeInsets.symmetric(horizontal: 15),
                child: SizedBox(
                  height: 200.0,
                  child: Shimmer.fromColors(
                    highlightColor: Colors.white30,
                    baseColor: Colors.grey.shade300,
                    child: Container(
                        width: 50.0,
                        height: 200.0,
                        margin: const EdgeInsets.symmetric(vertical: 7.5),
                        color: Colors.grey),
                  ),
                ));
          },
        ),
      ),
    );
  }
}
