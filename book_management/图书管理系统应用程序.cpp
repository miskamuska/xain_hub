#include <iostream>
#include <cstring>
#include<fstream>
#include"book.h"
#include"menu.h"
#include"user.h"
using namespace std; 
int div0(int c){
	if(c!=1&&c!=2&&c!=3)
		throw c;
}
int main(){
	while(1){
	menu11();
	int n1;
	string m1,m2;
	cin>>n1;
	try{
		div0(n1);
	}
	catch(int){
		cout<<"��������"<<endl;
	}
	switch(n1){
		case 1:{//��ѧ����ݵ�¼ 
		m1=slogin(); 
		if(m1!="0"&&m1!="-1"){
		while(1){
			menu21();
			int a1;
			cin>>a1;
			switch(a1){
				case 1:{//�鿴ͼ���б� 
					list();
					break;
				}
				case 2:{//����ͼ�� 
					list(); 
					Book b1;
					string a1;
					int a0;
					cout<<"����ͼ��"<<endl; 
					cout<<"��������Ҫ����ͼ��ı��"<<endl;
					cin>>a1;
					try{
						div1(a1);
					}
					catch(int){
						cout<<"���鲻����"<<endl;
						break;
					}
					cout<<"��Ҫ���ĵ��ǣ�"<<getn(a1)<<endl;
					cout<<"ȷ����1"<<"ȡ����2"<<endl;
					cin>>a0;
					if(a0==1){
					b1.setc(a1);
					b1.jie(m1);
					}
					;
			
					break;
				}
				case 3:{//�黹ͼ�� 
					mylend(m1); 
					int a0;
					Book b1;
					string a1;
					cout<<"�黹ͼ��"<<endl; 
					cout<<"��������Ҫ�黹ͼ��ı��"<<endl;
					cin>>a1;
					try{
						div3(m1,a1);
					}
					catch(int){
						cout<<"���鲻����"<<endl;
						break;
					}
					cout<<"��Ҫ�黹�ĵ��ǣ�"<<getn(a1)<<endl;
					cout<<"ȷ����1"<<"ȡ����2"<<endl;
					cin>>a0;
					if(a0==1)
					{
					b1.setc(a1);
					b1.huan(m1); 
					}
					
					break;
				} 
				case 4:{ //�鿴��Ǽ�¼ 
					mylend(m1); 
					break;
				}	 
				case 5:{
					schange(m1);
					break;
				} 
			}
			if(a1!=0&&a1!=1&&a1!=2&&a1!=3&&a1!=4&&a1!=5){
				cout<<"��������"<<endl;
			} 
			if(a1==0)
			break; 
		}				
	}
	break;
		}
		case 2:{//�Թ���Ա��ݵ�¼ 
		m2=mlogin(); 
		if(m2!="0"&&m2!="-1"){
			while(1){
			menu22();
			int a2;
			cin>>a2;
		switch(a2){
			case 1:{
				list();
				break;
			}
			case 2:{//����ͼ�� 
				int a0;
				cout<<"���Ӳ���"<<endl;
				cout<<"��ǰ���飺"<<endl;
				list();
				string s1,s2;int s3;
				cout<<"����������"<<endl;
				cin>>s1;
				cout<<"��������"<<endl;
				cin>>s2;
				cout<<"����������"<<endl;
				cin>>s3;
				try{
					div2(s2);
				}
				catch(int){
					cout<<"�����Ѵ���"<<endl;
					break;
				}
				cout<<"��Ҫ���ӵĵ��ǣ�"<<s1<<' '<<s2<<' '<<s3<<"��"<<endl;
				cout<<"ȷ����1"<<"ȡ����2"<<endl;
				cin>>a0;
				if(a0==1){
				Book b1(s1,s2,s3);
				b1.add(b1);	
				}
				cout<<"��ǰ���飺"<<endl;
				list(); 
				break;
			}
			case 3:{//ɾ��ͼ�� 
				int a0;
				cout<<"ɾ������"<<endl;	
				cout<<"��ǰ���飺"<<endl;
				list();
				Book b1;
				string s2;
				cout<<"������ָ��ͼ����"<<endl;
				cin>>s2;
				try{
						div1(s2);
					}
					catch(int){
						cout<<"���鲻����"<<endl;
						break;
					}
				cout<<"��Ҫɾ���ĵ��ǣ�"<<getn(s2)<<' '<<s2<<endl;
				cout<<"ȷ����1"<<"ȡ����2"<<endl;
				cin>>a0;
				if(a0==1){
				b1.setc(s2);
				b1.del();
				}
				
				break;
			}
			case 4:{//�޸�ͼ�� 
				cout<<"�޸Ĳ���"<<endl;	
				cout<<"��ǰ���飺"<<endl;
				list();
				Book b1;
				string s2;
				cout<<"������ָ��ͼ����"<<endl;
				cin>>s2;
				try{
						div1(s2);
					}
					catch(int){
						cout<<"���鲻����"<<endl;
						break;
					}
				b1.setc(s2);
				b1.xiu();
				cout<<"��ǰ���飺"<<endl;
				list();
				break;
			} 
			case 5:{//�鿴����б� 
				 lendlist(); 
				break;
			} 
			case 6:{
				mchange(m2);
				break;
			}
		}
		if(a2!=0&&a2!=1&&a2!=2&&a2!=3&&a2!=4&&a2!=5&&a2!=6){
				cout<<"��������"<<endl;
			}
		if(a2==0)
			break; 
	}
		break;
		}
		break;
	}
		case 3:{//ע�� 
				menu12();
				int n2;
				
				
				cin>>n2;
				if(n2==1){
					srigister();
				}
				else if(n2==2){
					mrigister();
				}
				else{
					cout<<"��������"<<endl;
				break;
				}
				
		}
		case 0:{
			cout<<"ллʹ��"<<endl;
			exit(0);
			break;
		}
		
	} 
	}
}

