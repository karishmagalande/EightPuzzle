/*
 * 8-puzzle solver using A* search.
 * h(n) = number of misplaced tiles (blank not counted), g(n) = moves so far.
 * Tiles are entered row by row, with 0 for the blank.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
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
	// two states are the same if the tiles are in the same places
	@Override
	public boolean equals(Object o)
	{
		return (o instanceof state) && Arrays.equals(arr,((state)o).arr);
	}
	@Override
	public int hashCode()
	{
		return Arrays.hashCode(arr);
	}
}

public class EightPuzzle{
	state gs,cs,ss;
	ArrayList<state>openlist=new ArrayList<state>();
	HashSet<state>closelist=new HashSet<state>();
	HashSet<state>openset=new HashSet<state>();     // same states as openlist, for fast lookup
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
			if(s.arr[i]!=0 && s.arr[i]!=gs.arr[i])
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
			if(!closelist.contains(ns) && !openset.contains(ns))
			{
				openlist.add(ns);
				openset.add(ns);
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
			if(!closelist.contains(ns) && !openset.contains(ns))
			{
				openlist.add(ns);
				openset.add(ns);
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
			if(!closelist.contains(ns) && !openset.contains(ns))
			{
				openlist.add(ns);
				openset.add(ns);
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
			if(!closelist.contains(ns) && !openset.contains(ns))
			{
				openlist.add(ns);
				openset.add(ns);
				display(ns);
				System.out.println("f(n)="+ns.f+"\n");
			}
		}
		
	}
	
	// parity of inversions (blank ignored); start and goal must match for a solution to exist
	int inversions(state s)
	{
		int inv=0;
		for(int i=0;i<9;i++)
			for(int j=i+1;j<9;j++)
				if(s.arr[i]!=0 && s.arr[j]!=0 && s.arr[i]>s.arr[j])
					inv++;
		return inv;
	}
	
	public void astar()
	{
		if(inversions(ss)%2!=inversions(gs)%2)
		{
			System.out.println("\nThis start state cannot reach the goal state.");
			return;
		}
		int low=0;
		ss.f=h(ss);
		openlist.add(ss);
		openset.add(ss);
		while(!openlist.isEmpty())
		{
			low=lowest();
			System.out.println("\nExpanding state with lowest f(n) : ");
			cs=openlist.get(low);
			display(cs);
			System.out.println();
			closelist.add(cs);
			openlist.remove(low);
			openset.remove(cs);
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
	
	public static void main(String args[])
	{
		EightPuzzle p=new  EightPuzzle();
		p.input();
		p.astar();
	
   }
}
