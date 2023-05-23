package com.example.unblockme10x10;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class Block extends androidx.appcompat.widget.AppCompatButton {

    ArrayList<Integer> sectionList = new ArrayList<Integer>();    //shows that the block is related to which section(s)
    int sectionSize;
    int blockNum;       // number of block given in input. 0 means space block, -1 means space block that is hidden (game block is set to that location), 100 is goal block. Also, it is used to determine exitGate. Number of exitGate block shows which section is it for
    int orientation;    //-1 means vertical, 1 means horizontal & 0 means both
    int width;    //Number of sections of block in width
    int height;   //Number of sections of block in height
    int coordinate_X;   //Horizontally number of starting section of block (x_determiner * sectionSize results x of block)
    int coordinate_Y;   //Same as above for y

    public Block(Context context) {
        super(context);
    }

    int orientationSetter(ArrayList<Integer> Sections) {
        Collections.sort(Sections);
        if (Sections.size() == 0)       // for null blocks of blockArray
        {
            return 0;
        }
        else if (Sections.size() == 1) {
            return 0;
        }
        else if (Sections.get(1) == Sections.get(0) + 1)     //if section numbers are consecutive (e.g. 7,8,9) it means block is horizental
        {
            return 1;
        }
        else {
            return -1;
        }
    }

    int widthSetter(ArrayList<Integer> Sections, int sectionSize, int orientation) {
        if (Sections.size() == 0) {
            width = 0;      // For null blocks set width to 0.
            return width;
        }
        if (orientation == 0) {
            width = sectionSize;
        } else if (orientation == 1) {
            width = Sections.size()*sectionSize;
        } else {
            width = sectionSize;
        }
        return width;
    }

    int heightSetter(ArrayList<Integer> Sections, int sectionSize, int orientation) {
        if (Sections.size() == 0) {
            height = 0;      // For null blocks set height to 0.
            return height;
        }
        if (orientation == 0) {
            height = sectionSize;
        } else if (orientation == -1) {
            height = Sections.size() * sectionSize;
        } else {
            height = sectionSize;
        }
        return height;
    }

    int x_Setter(ArrayList<Integer> Sections, int sectionSize) {
        if (Sections.size() == 0) {
            coordinate_X = 0;      // For null blocks set x to 0.
            return coordinate_X;
        }
        Collections.sort(Sections);
        return (Sections.get(0)%10)*(sectionSize);
    }

    int y_Setter(ArrayList<Integer> Sections, int sectionSize) {
        if (Sections.size() == 0) {
            coordinate_Y = 0;      // For null blocks set y to 0.
            return coordinate_Y;
        }
        Collections.sort(Sections);
        return (Sections.get(0)/10)*(sectionSize);
    }

}

