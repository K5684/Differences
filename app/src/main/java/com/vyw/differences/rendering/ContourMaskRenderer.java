package com.vyw.differences.rendering;

import com.vyw.differences.imaging.IFrame;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ContourMaskRenderer implements IRenderer {

    private Scalar contourColour;

    public ContourMaskRenderer() {
        setDefaultColour();
    }

    @Override
    public void display(IFrame inputFrame) {

        Imgproc.drawContours(inputFrame.getRGBA(),
                inputFrame.getContours(),
                0,
                contourColour,
                -1);

    }

    private void setDefaultColour(){
        contourColour = new Scalar(0,255,0,255);
    }

}
