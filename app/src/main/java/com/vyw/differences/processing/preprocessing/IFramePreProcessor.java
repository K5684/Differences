package com.vyw.differences.processing.preprocessing;

import com.vyw.differences.imaging.IFrame;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;


public interface IFramePreProcessor {

    IFrame preProcess(CvCameraViewFrame inputFrame);

}
