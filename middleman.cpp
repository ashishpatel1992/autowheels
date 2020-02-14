#include<ros/ros.h>
#include<std_msgs/String.h>
#include<bits/stdc++.h>
#define mid (tl+tr)/2
#define mod 1000000007
#define fain(arr) for(int i=0;i<n;i++)cin>>arr[i];
#define faio(arr) for(int i=0;i<n;i++)cout<<arr[i]<<" ";
#define all(x) x.begin(),x.end()
#define SPEED ios::sync_with_stdio(false); cin.tie(0); cout.tie(0);
#define bugv(n1) cout<<#n1<<" is "<<n1<<'\n';
#define FILE freopen("input.txt","r",stdin);freopen("output.txt","w",stdout);
#define rep(i, begin, end) for (__typeof(end) i = (begin) - ((begin) > (end)); i != (end) - ((begin) > (end)); i += 1 - 2 * ((begin) > (end)))
#define endl '\n'
#define ll long long int
#define pii pair<int,int>
#define pll pair<long long int,long long int>
#define pi acos(-1)
#define sz(x) ((int)x.size())
#define clr(x) memset(x, 0, sizeof(x))
// #define init(x, a) memset(x, a, sizeof(x))
using namespace std;

std_msgs::String smsg;
std_msgs::String rmsg;
std_msgs::String r2msg;
ros::Publisher pb;
int tolerance = 5;
double wheelangle=0;
double chairangle=0;
double x_c=0;
double y_c=0;
int x_block=0;
int y_block=0;
int xlimit = 5;
int ylimit = 5;
int blockindex=0;
double wheelradius = 0.5;
char mode = 'A';
set<pair<int,int> > blocks;
int block_size=10;
vector<pii> spath;
pii addpair(pii a,pii b)
{
	pii ans;
	ans.first = a.first + b.first;
	ans.second = a.second + b.second;
	return ans;
}

bool isvalid(pii a)
{
	bool flag1 = blocks.find(a)!=blocks.end();
	bool flag2 = a.first<=xlimit && a.first >=0 ;
	bool flag3 = a.second<=ylimit && a.second>=0 ;
	return flag1&&flag2&&flag3;
}


vector<pii> BFS(pii start,pii end)
{
	set<pii> visited;
	vector<pii> path;
	queue<pii> q;
	q.push(start);
	visited.insert(start);
	map<pii,pii> parent;
	parent[start]=start;
	pii delta[4] = {{0,1},{1,0},{-1,0},{0,-1}};
	while(!q.empty())
	{
		pii xx = q.front();
		q.pop();
		for(int i=0;i<4;i++)
		{
			pii npair = addpair(xx,delta[i]);
			if((isvalid(npair)&&(visited.find(npair))==visited.end()))
			{
				q.push(npair);
				visited.insert(npair);
				parent[npair]=xx;
			}
		}
	}
	pii ff = end;
	while(parent[ff]!=ff)
	{
		path.push_back(ff);
		ff=parent[ff];
	}
	path.push_back(ff);
	reverse(all(path));
	return path;
}

bool istop(pii a,pii b)
{
	return (b.first==a.first)&&(b.second==a.second+1);
}

bool isbottom(pii a,pii b)
{
	return (b.first==a.first)&&(b.second==a.second-1);
}

bool isleft(pii a,pii b)
{
	return (b.second==a.second)&&(b.first==a.first-1);
}

bool isright(pii a,pii b)
{
	return (b.second==a.second)&&(b.first==a.first+1);
}

string dataout;

bool isbetween(int a,int b)
{
	return chairangle>=min(a,b)&&chairangle<=max(a,b);
}

void nextmove()
{
	pii current;
	current.first=x_block;
	current.second = y_block;

	if((x_block==spath[blockindex].first)&&(y_block==spath[blockindex].second))
	{
		blockindex++;
		if(blockindex>=spath.size())
			return;
	}

	if(istop(current,spath[blockindex]))
	{
		if(isbetween(90-tolerance,90+tolerance))
		{
			dataout="forward";
		}
		else if(isbetween(0,90-tolerance))
		{
			dataout="left";
		}
		else if(isbetween(90+tolerance,180))
		{
			dataout="right";
		}
		else if(isbetween(180,270))
		{
			dataout="right";
		}
		else if(isbetween(270,360))
		{
			dataout="left";
		}
		else
		{
			dataout="left";
		}	
	}
	else if(isright(current,spath[blockindex]))
	{
		if(isbetween(tolerance,0))
		{
			dataout="forward";
		}
		else if(isbetween(360,360-tolerance))
		{
			dataout="forward";
		}
		else if(isbetween(tolerance,180))
		{
			dataout="right";
		}
		else
		{
			dataout="left";
		}	
	}
	else if(isleft(current,spath[blockindex]))
	{
		if(isbetween(180-tolerance,180+tolerance))
		{
			dataout="forward";
		}
		else if(isbetween(0,180-tolerance))
		{
			dataout="left";
		}
		else if(isbetween(tolerance+180,360))
		{
			dataout="right";
		}
		else
		{
			dataout="left";
		}	
	}
	else if(isbottom(current,spath[blockindex]))
	{
		if(isbetween(270-tolerance,270+tolerance))
		{
			dataout="forward";
		}
		else if(isbetween(90,270-tolerance))
		{
			dataout="left";
		}
		else
		{
			dataout="right";
		}	
	}
}


void changer(double xcor,double ycor,double angle)
{

	chairangle=angle;
	x_c  = xcor;
	y_c = ycor;

	cout<<x_c<<" "<<y_c<<endl;
	x_block = x_c / block_size;
	y_block = y_c / block_size;

	blocks.insert({x_block,y_block});
}

int string_to_int(string s)
{
	int ans=0;
	for(int i=0;i<s.length();i++)
	{
		ans*=10;
		ans+=s[i]-'0';
	}
}

void display()
{
	for(int j=14;j>=0;j--)
	{
		for(int i=0;i<15;i++)
		{
			if(i==x_block&&j==y_block)
			{
				cout<<"C"<<" ";
			}
			else if(blocks.find({i,j})!=blocks.end())
			{
				cout<<"D"<<" ";
			}
			else
			{
				cout<<"N"<<" ";
			}
		}
		cout<<endl;
	}
}



void Solve()
{
	//ROS_INFO("Solving now .... \n");
	string datain = rmsg.data.c_str();
	cout<<datain<<endl;
	string xcor="";
	string ycor="";
	string angle = r2msg.data.c_str();
	int i=0;
	while(datain[i]!='_')
	{	
		xcor.push_back(datain[i]);
		i++;
	}
	i++;
	while(i<datain.length())
	{
		ycor.push_back(datain[i]);
		i++;
	}
    cout<<xcor<<" "<<ycor<<endl;
    stringstream ss1(xcor);
    stringstream ss2(ycor);
    stringstream ss3(angle);
    double xx;
    double yy;
    double aa;
    ss1>>xx;
    ss2>>yy;
    ss3>>aa; 
	changer(xx,yy,aa);
	//nextmove();
	display();
	smsg.data=dataout;
	pb.publish(smsg);
	
}
void callBack(const std_msgs::String::ConstPtr& msg)
{
	//ROS_INFO(" Recieved %s from mouse",msg->data.c_str());
	rmsg = *msg;
	Solve();
}

void call2(const std_msgs::String::ConstPtr& msg)
{
	//ROS_INFO(" Recieved %s from mouse",msg->data.c_str());
	r2msg = *msg;
}
pii destination;
void setup()
{
	blocks.insert({0,0});
	blocks.insert({1,0});
	blocks.insert({1,1});
	blocks.insert({1,2});
	blocks.insert({1,3});
	blocks.insert({1,4});
	blocks.insert({2,4});
	blocks.insert({3,4});
	blocks.insert({3,3});
	blocks.insert({3,2});
	blocks.insert({3,1});
	blocks.insert({3,0});
	blocks.insert({4,0});
	blocks.insert({5,0});
	//spath = BFS({0,0},destination);
}



int main(int argc,char**argv)
{
	ros::init(argc,argv,"middle");
	ros::NodeHandle nh;
	ros::NodeHandle nh2;
	ros::NodeHandle nh3;
	// FILE;
	cin>>destination.second;
	setup();	
	ros::Subscriber sb = nh.subscribe("mouse_in",1000,callBack) ;
	ros::Subscriber sb2 = nh3.subscribe("imu_in",1000,call2);
	pb = nh2.advertise<std_msgs::String>("output",1000) ;
	ros::spin();
}
