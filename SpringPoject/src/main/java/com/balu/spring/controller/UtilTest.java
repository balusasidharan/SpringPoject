package com.balu.spring.controller;

public class UtilTest {
	private static boolean availableSocialMedia  = false;
	private static boolean installedSocialMedia = false ;

	public static boolean isSocialMediaProduct(){
		boolean isSocialMediaEnable = false;
	
		System.out.println(Thread.currentThread().getName()+".....availableSocialMedia: "+ availableSocialMedia);
		System.out.println(Thread.currentThread().getName()+".....installedSocialMedia: "+ installedSocialMedia);
		
		if(availableSocialMedia || installedSocialMedia){
			isSocialMediaEnable = true;
		}
		return isSocialMediaEnable;
	}
	
	public static void setbooleans(){
		System.out.println(Thread.currentThread().getName()+"setting booleans to true");
		availableSocialMedia = true;
		installedSocialMedia = true;
		
	}
}
