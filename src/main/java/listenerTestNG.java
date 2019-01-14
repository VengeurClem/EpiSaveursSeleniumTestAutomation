package main.java;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class listenerTestNG implements ITestListener {
    @Override
    public void onTestStart(ITestResult Result) {

    }

    @Override
    public void onTestSuccess(ITestResult Result)
    {
        System.out.println("The name of the testcase passed is :"+Result.getName());
    }

    @Override
    public void onTestFailure(ITestResult Result)
    {
        System.out.println("The name of the testcase failed is :"+Result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult Result)
    {
        System.out.println("The name of the testcase Skipped is :"+Result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {

    }

    @Override
    public void onStart(ITestContext Context) {

    }

    @Override
    public void onFinish(ITestContext Context) {

    }
}
