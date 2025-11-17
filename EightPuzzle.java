/*
 * ASSIGNMENT NO. :5
 * Name:Karishma Galande
 * Roll no. :3317
 * Title :Implement 8 puzzle using A*.
 * ===========================================================================================================
 */
import java.util.ArrayList;
import java.util.Scanner;
 class state {
	int arr[];
	state ptr=null;
	int f=0,g=0;
	 state(state s)
	{
		this.arr=new int[9];
		for(int i=0;i<9;i++)
		{
			this.arr[i]=s.arr[i];
		}
	}
	state()
	{
		this.arr=new int[9];
	}
}

public class EightPuzzle{
	state gs,cs,ss;
	ArrayList<state>openlist=new ArrayList<state>();
	ArrayList<state>closelist=new ArrayList<state>();
	 EightPuzzle()
	{
		gs=new state();
		cs=new state();
		ss=new state();
	}
	public void input()
	{
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter start state : ");
		for(int i=0;i<9;i++)
		{
			ss.arr[i]=scan.nextInt();
		}
		System.out.println("Enter Goal state : ");
		for(int i=0;i<9;i++)
		{
			gs.arr[i]=scan.nextInt();
		}
		System.out.println("Start state : ");
		display(ss);
		System.out.println("Goal state : ");
		display(gs);
	}
	
	public int h(state s)
	{
		int h=0;
		for(int i=0;i<9;i++)
		{
			if(s.arr[i]!=gs.arr[i])
			{
				h++;
			}
		}
		return h;
	}
	
	public int lowest()
	{
		int min=0,p=0;
		min=openlist.get(0).f;
		for(int i=0;i<openlist.size();i++)
		{
			if(min>openlist.get(i).f)
			{
				min=openlist.get(i).f;
				p=i;
			}
		}
		return p;
	}
	
	public void display(state s)
	{
		for(int i=0;i<9;i++)
		{
			if(i==3 || i==6 || i==9)
				System.out.println();
			System.out.print(" "+s.arr[i]);
		}
		System.out.println();
	}
	
	public void move(state s)
	{
		int p=0;
		for(int i=0;i<9;i++)
		{
			if(s.arr[i]==0)
			{
				p=i;
				break;
			}
		}
		if(p%3!=0)                          //left
		{
			state ns=new state(s);
			ns.arr[p]=ns.arr[p-1];
			ns.arr[p-1]=0;
			ns.g=s.g+1;
			ns.f=ns.g+h(ns);
			ns.ptr=s;
			if(!closelist.contains(ns))
			{
				openlist.add(ns);
				display(ns);
				System.out.println("f(n)="+ns.f);
			}
		}
		if(p%3!=2)                         //right
		{
			state ns=new state(s);
			ns.arr[p]=ns.arr[p+1];
			ns.arr[p+1]=0;
			ns.g=s.g+1;
			ns.f=ns.g+h(ns);
			ns.ptr=s;
			if(!closelist.contains(ns))
			{
				openlist.add(ns);
				display(ns);
				System.out.println("f(n)="+ns.f);
			}
		}
		if(p<6)                       //down
		{
			state ns=new state(s);
			ns.arr[p]=ns.arr[p+3];
			ns.arr[p+3]=0;
			ns.g=s.g+1;
			ns.f=ns.g+h(ns);
			ns.ptr=s;
			if(!closelist.contains(ns))
			{
				openlist.add(ns);
				display(ns);
				System.out.println("f(n)="+ns.f);
			}
		}
		if(p>2)                    //up
		{
			state ns=new state(s);
			ns.arr[p]=ns.arr[p-3];
			ns.arr[p-3]=0;
			ns.g=s.g+1;
			ns.f=ns.g+h(ns);
			ns.ptr=s;
			if(!closelist.contains(ns))
			{
				openlist.add(ns);
				display(ns);
				System.out.println("f(n)="+ns.f+"\n");
			}
		}
		
	}
	
	public void astar()
	{
		int low=0;
		ss.f=h(ss);
		openlist.add(ss);
		while(!openlist.isEmpty())
		{
			low=lowest();
			System.out.println("\ncurrent States with  heuristic function : ");
			cs=openlist.get(low);
			display(cs);
			System.out.println();
			closelist.add(cs);
			openlist.remove(cs);
			if(h(cs)==0)
			{
				ArrayList<state>path=new ArrayList<state>();
				state temp=new state();
				temp=cs;
				path.add(temp);
				temp=temp.ptr;
				while(temp!=null)
				{
					path.add(temp);
					temp=temp.ptr;
				}
				System.out.println("Path Size : "+path.size());
				for(int i=path.size()-1;i>=0;i--)
				{
					display(path.get(i));
					path.remove(i);
					System.out.println();
				}
				break;
			}
			else
			{
				move(cs);
			}
			System.out.println("**************************************");
		}
	}
	
	public static void main(String srgs[])
	{
		EightPuzzle p=new  EightPuzzle();
		p.input();
		p.astar();
	
   }
}
/***********OUTPUT**********
 * 
 Enter start state : 
1
2
3
0
5
6
4
7
8
Enter Goal state : 
1
2
3
4
5
6
7
8
0

State with lowest heuristic function : 
 1 2 3
 0 5 6
 4 7 8

 1 2 3
 5 0 6
 4 7 8
f(n)=6
 1 2 3
 4 5 6
 0 7 8
f(n)=4
 0 2 3
 1 5 6
 4 7 8
f(n)=6

**************************************

State with lowest heuristic function : 
 1 2 3
 4 5 6
 0 7 8

 1 2 3
 4 5 6
 7 0 8
f(n)=4
 1 2 3
 0 5 6
 4 7 8
f(n)=6

**************************************

State with lowest heuristic function : 
 1 2 3
 4 5 6
 7 0 8

 1 2 3
 4 5 6
 0 7 8
f(n)=6
 1 2 3
 4 5 6
 7 8 0
f(n)=3
 1 2 3
 4 0 6
 7 5 8
f(n)=6

**************************************

State with lowest heuristic function : 
 1 2 3
 4 5 6
 7 8 0

Path Size : 4
 1 2 3
 0 5 6
 4 7 8

 1 2 3
 4 5 6
 0 7 8

 1 2 3
 4 5 6
 7 0 8

 1 2 3
 4 5 6
 7 8 0


***************************************************************

Enter start state : 
1
3
4
8
6
2
7
0
5
Enter Goal state : 
1
2
3
8
0
4
7
6
5
Start state : 
 1 3 4
 8 6 2
 7 0 5
Goal state : 
 1 2 3
 8 0 4
 7 6 5

State with lowest heuristic function : 
 1 3 4
 8 6 2
 7 0 5

 1 3 4
 8 6 2
 0 7 5
f(n)=7
 1 3 4
 8 6 2
 7 5 0
f(n)=7
 1 3 4
 8 0 2
 7 6 5
f(n)=4

**************************************

State with lowest heuristic function : 
 1 3 4
 8 0 2
 7 6 5

 1 3 4
 0 8 2
 7 6 5
f(n)=7
 1 3 4
 8 2 0
 7 6 5
f(n)=6
 1 3 4
 8 6 2
 7 0 5
f(n)=7
 1 0 4
 8 3 2
 7 6 5
f(n)=6

**************************************

State with lowest heuristic function : 
 1 3 4
 8 2 0
 7 6 5

 1 3 4
 8 0 2
 7 6 5
f(n)=6
 1 3 4
 8 2 5
 7 6 0
f(n)=8
 1 3 0
 8 2 4
 7 6 5
f(n)=6

**************************************

State with lowest heuristic function : 
 1 0 4
 8 3 2
 7 6 5

 0 1 4
 8 3 2
 7 6 5
f(n)=8
 1 4 0
 8 3 2
 7 6 5
f(n)=7
 1 3 4
 8 0 2
 7 6 5
f(n)=6
**************************************

State with lowest heuristic function : 
 1 3 4
 8 0 2
 7 6 5

 1 3 4
 0 8 2
 7 6 5
f(n)=9
 1 3 4
 8 2 0
 7 6 5
f(n)=8
 1 3 4
 8 6 2
 7 0 5
f(n)=9
 1 0 4
 8 3 2
 7 6 5
f(n)=8

**************************************

State with lowest heuristic function : 
 1 3 0
 8 2 4
 7 6 5

 1 0 3
 8 2 4
 7 6 5
f(n)=6
 1 3 4
 8 2 0
 7 6 5
f(n)=8
**************************************

State with lowest heuristic function : 
 1 3 4
 8 0 2
 7 6 5

 1 3 4
 0 8 2
 7 6 5
f(n)=9
 1 3 4
 8 2 0
 7 6 5
f(n)=8
 1 3 4
 8 6 2
 7 0 5
f(n)=9
 1 0 4
 8 3 2
 7 6 5
f(n)=8

**************************************

State with lowest heuristic function : 
 1 0 3
 8 2 4
 7 6 5

 0 1 3
 8 2 4
 7 6 5
f(n)=8
 1 3 0
 8 2 4
 7 6 5
f(n)=8
 1 2 3
 8 0 4
 7 6 5
f(n)=5
**************************************

State with lowest heuristic function : 
 1 2 3
 8 0 4
 7 6 5

Path Size : 6
 1 3 4
 8 6 2
 7 0 5

 1 3 4
 8 0 2
 7 6 5

 1 3 4
 8 2 0
 7 6 5

 1 3 0
 8 2 4
 7 6 5

 1 0 3
 8 2 4
 7 6 5

 1 2 3
 8 0 4
 7 6 5

 * */
 