#include"user.h"
#include <iostream>
#include <cstring>
#include<fstream>
#include"book.h"
using namespace std; 
int srigister(){
	string name,id,psword1,psword2;
	cout<<"ѧ��ע�᣺"<<endl; 
	cout<<"����������"<<endl; 
	cin>>name;
	cout<<"������ѧ��"<<endl; 	
	cin>>id;
	if(div5(id)==1){
		cout<<"�û��Ѵ���"<<endl;
		exit(0);
	}
	cout<<"����������"<<endl; 	
	cin>>psword1;
	cout<<"���ٴ���������"<<endl; 	
	cin>>psword2;
	if(psword1==psword2){
		ofstream fout("E:\\student.txt",ios::app);
		fout<<name<<endl;
		fout<<id<<endl;
		fout<<psword1<<endl;
		fout.close();	
		cout<<"ע��ɹ�"<<endl;	
	} 
	else{
		cout<<"�������벻һ��"<<endl;
	}
} 
string slogin(){
	int i=0;
	string id,psword;
	string temp1,temp2,temp3;
		cout<<"������ѧ��"<<endl; 	
	cin>>id;
	cout<<"����������"<<endl; 	
	cin>>psword;
	ifstream fin("E:\\student.txt",ios::in);
	while(getline(fin,temp1)){
		getline(fin,temp2);
		getline(fin,temp3);
		if(temp2==id){
			i++;
			if(temp3==psword){
				//string c=getname(temp2);
					cout<<"��ӭ��"<<temp1<<endl;
			cout<<"��½�ɹ�"<<endl;
				return temp1;
			}
			else{
				cout<<"�������"<<endl;
				return "0";
			}
		}
		
	}
	if(i==0){
			cout<<"�û�������"<<endl;
			return "-1";
		}
	fin.close();
}
int mrigister(){
string name,id,num,psword1,psword2;
cout<<"����Աע�᣺"<<endl; 
	cout<<"����������"<<endl; 
	cin>>name;
	cout<<"�����빤��"<<endl; 	
	cin>>id;
	if(div6(id)==1){
		cout<<"�û��Ѵ���"<<endl;
		exit(0);
	}
	cout<<"��������֤��"<<endl; 
	cin>>num;
	if(num=="1234"){
		cout<<"����������"<<endl; 	
	cin>>psword1;
	cout<<"���ٴ���������"<<endl; 	
	cin>>psword2;
	if(psword1==psword2){
		ofstream fout("E:\\manager.txt",ios::app);
		fout<<name<<endl;
		fout<<id<<endl;
		fout<<psword1<<endl;
		cout<<"ע��ɹ�"<<endl;
		fout.close();		
	} 
	else{
		cout<<"�������벻һ��"<<endl;
	}
	}
	else{
		cout<<"��֤�����"<<endl;
	}
	
} 
string mlogin(){
	int i=0;
	string id,psword;
	string temp1,temp2,temp3;
		cout<<"�����빤��"<<endl; 	
	cin>>id;
	cout<<"����������"<<endl; 	
	cin>>psword;
	ifstream fin("E:\\manager.txt",ios::in);
	while(getline(fin,temp1)){
		getline(fin,temp2);
		getline(fin,temp3);
		if(temp2==id){
			if(temp3==psword){
				cout<<"��½�ɹ�"<<endl;
				cout<<"��ӭ��"<<' '<<"Mr."<<temp1<<endl;
				i++;
				return temp1;
			}
			else{
				cout<<"�������"<<endl;
				return "0";
			}
		}
	}
	if(i==0){
		cout<<"�û���������"<<endl;
		return "-1";
	}
	fin.close();
}
string getcode(string n){
	string temp1,temp2,temp3;	
	ifstream fin("E:\\student.txt",ios::in);
	while(getline(fin,temp1)){
		if(temp1!="0"){
		getline(fin,temp2);
		getline(fin,temp3);
		if(temp1==n){
		return temp2;	
		}	
		}
		else{
		getline(fin,temp2);
		getline(fin,temp3);
		}
	}
	fin.close();
}
int div5(string c){
	int i=0;
	string temp1,temp2,temp3;	
	ifstream fin("E:\\student.txt",ios::in);
	while(getline(fin,temp1)){
		if(temp1!="0"){
		getline(fin,temp2);
		getline(fin,temp3);
		if(temp2==c){
		i++;
		break;	
		}	
		}
		else{
		getline(fin,temp2);
		getline(fin,temp3);
		}	
	}
	if(i>0){
		return 1;
	}
	else{
		return 0;
	}
	fin.close();
}
int div6(string c){
	int i=0;
	string temp1,temp2,temp3;	
	ifstream fin("E:\\manager.txt",ios::in);
	while(getline(fin,temp1)){
		if(temp1!="0"){
		getline(fin,temp2);
		getline(fin,temp3);
		if(temp2==c){
		i++;
		break;	
		}	
		}
		else{
		getline(fin,temp2);
		getline(fin,temp3);
		}	
	}
	if(i>0){
		return 1;
	}
	else{
		return 0;
	}
	fin.close();
}
void schange(string n){
	string str1,str2; 
	int i=0,j;
	cout<<"�������޸ĺ�����"<<endl;
	cin>>str1; 
	cout<<"�������޸ĺ�����"<<endl;
	cin>>str2;
	char str[7];
	for(j=0;j<str2.length();++j){
		str[j]=str2[j];
	}
	str[j]='\0';
	if(str1==str2) {
		string temp1,temp2,temp3;
		ifstream fin("E:\\student.txt",ios::in);
		ofstream fout("E:\\student.txt",ios::app);
		while(getline(fin,temp1)){
		if(temp1!="0"){
		getline(fin,temp2);
		getline(fin,temp3);
		i+=3;
		if(temp1==n){
			modifyContentInFile("E:/student.txt",i,str);
			cout<<"�޸ĳɹ�"<<endl;
		}	
		}
		else{
		getline(fin,temp2);
		getline(fin,temp3);
		i+=3;
		}	
	}
	}
	else{
		cout<<"�������벻һ��"<<endl;
	}
}
void mchange(string n){
	string str1,str2; 
	int i=0,j;
	cout<<"�������޸ĺ�����"<<endl;
	cin>>str1; 
	cout<<"�������޸ĺ�����"<<endl;
	cin>>str2;
	char str[7];
	for(j=0;j<str2.length();++j){
		str[j]=str2[j];
	}
	str[j]='\0';
	if(str1==str2) {
		string temp1,temp2,temp3;
		ifstream fin("E:\\manager.txt",ios::in);
		ofstream fout("E:\\manager.txt",ios::app);
		while(getline(fin,temp1)){
		if(temp1!="0"){
		getline(fin,temp2);
		getline(fin,temp3);
		i+=3;
		if(temp1==n){
			modifyContentInFile("E:/manager.txt",i,str);
			cout<<"�޸ĳɹ�"<<endl;
		}	
		}
		else{
		getline(fin,temp2);
		getline(fin,temp3);
		i+=3;
		}	
	}
	}
	else{
		cout<<"�������벻һ��"<<endl;
	}
}

