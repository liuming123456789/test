package com.liuming.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
  
@Entity
@Table(name="ut_adm_info")
   public class UtAdmInfoVo{
@Id 
@Basic(optional=false)
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="adm_id")
   private String admin_id;
@Column(name="adm_acc")
   private String admin_acc;
@Column(name="adm_name")
   private String admin_name;
@Column(name="adm_state")
   private String admin_state;
@Column(name="adm_pwd")
   private String admin_pwd;
   public void setAdm_id( String adm_id){
	    admin_id=adm_id;
   }
   public String getAdm_id(){
	   return admin_id;
   }
   public void setAdm_acc( String adm_acc){
	    admin_acc=adm_acc;
   }
   public String getAdm_acc(){
	   return admin_acc;
   }
   public void setAdm_name( String adm_name){
	    admin_name=adm_name;
   }
   public String getAdm_name(){
	   return admin_name;
   }
   public void setAdm_state( String adm_state){
	    admin_state=adm_state;
   }
   public String getAdm_state(){
	    return admin_state;
   }
   public void setAdm_pwd( String adm_pwd){
        admin_pwd=adm_pwd;
   }
   public String getAdm_pwd(){
        return admin_pwd;
   } 

}
