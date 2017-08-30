package com.uniovi.nmapgui.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.uniovi.nmapgui.model.Status.State;

@XmlRootElement(name = "nmaprun")
public class Scan {
	
	private List<Host> hosts;
	private List<Link> linkTraceroute=new ArrayList<Link>();

	@XmlElement(name="host")
	public List<Host> getHosts() {
		return hosts;
	}

	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}

	@Override
	public String toString() {
		return "Scan [Hosts:" + hosts + "]";
	}
	
	
	public void setLinkTraceroute(List<Link> linkTraceroute) {
		this.linkTraceroute = linkTraceroute;
	}

	public List<Link> getLinkTraceroute(){
		ArrayList <Link> list = new ArrayList<Link>();
		if(linkTraceroute!=null && linkTraceroute.isEmpty() && hosts!=null){
			Host you = new Host();
			you.getStatus().setState(State.you);
			you.setAddress(new Address("You"));
			for(Host host : hosts){
			   if(host.getTrace()==null || host.getTrace().size()<=1)
				   list.add(new Link(you,host));
			   else {
				   Host prev = you;
				   for(Hop hop: host.getTrace()){
					   list.add(new Link(prev, hop.toHost()));
					   prev=hop.toHost();
				   }
			   }
			}
			linkTraceroute=list;
		}
		return linkTraceroute;
	}

}
