ALTER TABLE secure_person_tbl
DROP CONSTRAINT fk_PerSecure;

ALTER TABLE saved_messages_tbl
DROP CONSTRAINT fk_PerMessage;

ALTER TABLE saved_messages_tbl
DROP CONSTRAINT fk_Message;

ALTER TABLE log_tbl
DROP CONSTRAINT fk_PerLog;

ALTER TABLE person_tasks_tbl
DROP CONSTRAINT fk_PerTask;

ALTER TABLE person_tasks_tbl
DROP CONSTRAINT fk_TaskTask;

Drop Table secure_person_tbl;
Drop Table saved_messages_tbl;
Drop table interested_houses_tbl;
Drop table log_tbl;
Drop Table messages_tbl;
Drop Table maintanence_tbl;
Drop table houses_tbl;
Drop table landlord_tbl;
Drop table person_tasks_tbl;
Drop table tasks_tbl;
Drop Table person_tbl;

create or replace type client_typ as object
(
  marital_status varchar2(100),
  no_of_children number(2)
);
/

create or replace type staff_typ as object
(
  job_title varchar2(100),
  salary number(7),
  manager_id number(5)
);
/

create or replace type address_typ as object
(
  house_number_and_street varchar2(100),
  town varchar2(100),
  county varchar2(100),
  country varchar2(100),
  postcode varchar2(50)
);
/

create table person_tbl
(
  person_id number(5) Primary Key,
  firstname varchar2(100) not null,
  surname varchar2(100) not null,
  person_address address_typ not null,
  client_info client_typ,
  staff_info staff_typ
);

create table secure_person_tbl
(
  person_id number(5),
  per_username varchar2(100),
  per_password varchar(100),
  CONSTRAINT fk_PerSecure FOREIGN KEY (person_id)
  REFERENCES person_tbl(person_id)
);

create table messages_tbl
(
  message_id number(5) Primary Key,
  sender_username varchar2(100) not null,
  recipient_username varchar2(100) not null,
  subject varchar2(200) not null,
  message varchar2(1000) not null,
  date_time varchar2(100) not null
);

create table saved_messages_tbl
(
  message_id number(5) not null,
  person_id number(5) not null,
  CONSTRAINT fk_PerMessage FOREIGN KEY (person_id)
  REFERENCES person_tbl(person_id),
  CONSTRAINT fk_Message FOREIGN KEY (message_id)
  REFERENCES messages_tbl(message_id)
);

create table tasks_tbl
(
  task_id number(5) Primary Key,
  task_name varchar2(100) not null,
  task_type varchar2(100) not null,
  description varchar2(500),
  task_address address_typ not null,
  date_time varchar2(100) not null,
  important varchar2(4) not null
);

create table person_tasks_tbl
(
  task_id number(5) not null,
  person_id number(5) not null,
  CONSTRAINT fk_PerTask FOREIGN KEY (person_id)
  REFERENCES person_tbl(person_id),
  CONSTRAINT fk_TaskTask FOREIGN KEY (task_id)
  REFERENCES tasks_tbl(task_id)
);

create table landlord_tbl
(
  landlord_id number(5) Primary Key,
  firstname varchar2(50) not null,
  surname varchar2(100) not null,
  landlord_address address_typ not null,
  home_phone varchar2(50) not null,
  mobile_phone varchar2(50),
  email varchar2(100)
);

create table houses_tbl
(
  house_id number(5) Primary Key,
  house_address address_typ not null,
  house_type varchar(100) not null,
  status varchar(100) not null,
  landlord_id number(5) not null,
  price_per_month number(6,2) not null,
  CONSTRAINT fk_LandHouse FOREIGN KEY (landlord_id)
  REFERENCES landlord_tbl(landlord_id)
);

create table interested_houses_tbl
(
  house_id number(5) not null,
  person_id number(5) not null,
  CONSTRAINT fk_PerInterested FOREIGN KEY (person_id)
  REFERENCES person_tbl(person_id),
  CONSTRAINT fk_HouseLog FOREIGN KEY (house_id)
  REFERENCES houses_tbl(house_id)
);

create table maintanence_tbl
(
  maintanence_id number(5) Primary Key,
  house_id number(5) not null,
  description varchar2(500) not null,
  severity varchar2(100) not null,
  DateOccurred varchar2(100) not null,
  DateCompleted varchar2(100)
);

create table log_tbl
(
  log_id number(5) Primary Key,
  person_id number(5) not null,
  log_type varchar(100) not null,
  description varchar2(500) not null,
  date_time varchar2(100) not null,
  CONSTRAINT fk_PerLog FOREIGN KEY (person_id)
  REFERENCES person_tbl(person_id)
);
   
Insert Into LANDLORD_TBL(LANDLORD_ID,FIRSTNAME,SURNAME,LANDLORD_ADDRESS,HOME_PHONE,MOBILE_PHONE,EMAIL)
  Select 1,'SGFyb2xk','QmxhaXI=',address_typ('MjEgSGFtbWVyc21pdGggUm9hZA==','QnJhZGZvcmQ=','WW9ya3NoaXJl','VUs=','QkQ1IDEwS1M='),'MDEyNzQ4NTcyNjM=','MDcyNjM0Mzc0Mzg=','YmxhaXJoQGhvdG1haWwuY28udWs=' From DUAL
  Union All Select 2,'Um9u','QnVyZ2VuZHk=',address_typ('MTIgSGlnaCBQZWFrIFJvYWQ=','QnV4dG9u','RGVyYnlzaGlyZQ==','VUs=','U0s3IDRSVA=='),'MDEyOTgxNDgyOTQ=','MDczNjUxNDM2NTQ=','dGhlb25seXJiQGdtYWlsLmNvLnVr' From DUAL
  Union All Select 3,'VGhvbWFz','R3J1bmR5',address_typ('MTggUGljYWRkaWxseSBBdmVudWU=','TWFuY2hlc3Rlcg==','R3JlYXRlciBNYW5jaGVzdGVy','VUs=','TTUgMVlX'),'MDE2MTkzODcxNjI=','MDc5NDg1NzE2MzM=','Z3J1bmR5dEBvdXRsb29rLmNvbQ==' From DUAL
  Union All Select 4,'UGhpbA==','R3JhdmVz',address_typ('MzYgTG9sbGlwb3AgTGFuZQ==','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA4U0I='),'MDE2MjU3Mzg0NzU=','MDc2MzQwNDAzODI=','Z3Jhdnlib2F0QGhvdG1haWwuY28udWs=' From DUAL
  Union All Select 5,'Q2xpdmU=','V29vZHdhcmQ=',address_typ('MTIxIEhhbW1lcnNtaXRoIFJvYWQ=','Q3JveWRvbg==','TG9uZG9u','VUs=','Q1IwIDFVUw=='),'MDIwODQ3NTYy','MDc0ODM3NDgwNTg=','d29vZHdhcmRjbGl2ZTM0QHlhaG9vLmNvLnVr' From DUAL;
  
Insert Into HOUSES_TBL(HOUSE_ID,HOUSE_ADDRESS,HOUSE_TYPE,STATUS,LANDLORD_ID,PRICE_PER_MONTH)
  Select 1,address_typ('MjMgU3VuZG93biBDbG9zZQ==','QnJpZ2h0b24=','RWFzdCBTdXNzZXg=','VUs=','Qk4yIDlHSA=='),'U2VtaS1EZXRhY2hlZA==','QXZhaWxhYmxlIHRvIFJlbnQ=',2,400 From DUAL
  Union All Select 2,address_typ('MTIgQ29uZ2VsdG9uIFJvYWQ=','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMCA1SEc='),'QnVuZ2Fsb3c=','QXZhaWxhYmxlIHRvIFJlbnQ=',4,350 From DUAL
  Union All Select 3,address_typ('MjUgQnJpYXdvb2QgQXZlbnVl','QnV4dG9u','RGVyYnlzaGlyZQ==','VUs=','U0s3IDVGUw=='),'RGV0YWNoZWQ=','QXZhaWxhYmxlIHRvIFJlbnQ=',1,600 From DUAL
  Union All Select 4,address_typ('MyBUb3Bpbmd0b24gUm9hZA==','Q3JveWRvbg==','TG9uZG9u','VUs=','Q1IyIDhDQg=='),'U2VtaS1EZXRhY2hlZA==','QXZhaWxhYmxlIHRvIFJlbnQ=',3,270 From DUAL
  Union All Select 5,address_typ('NTQgTWFuaW5ndG9uIExhbmU=','QnJpZ2h0b24=','Q2hlc2hpcmU=','VUs=','Qk4xIDVNUw=='),'QXBhcnRtZW50','QXZhaWxhYmxlIHRvIFJlbnQ=',5,420 From DUAL
  Union All Select 6,address_typ('MzIgQ2xhcHRyYXAgQ2xvc2U=','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA4TFM='),'QnVuZ2Fsb3c=','QXZhaWxhYmxlIHRvIFJlbnQ=',4,950 From DUAL
  Union All Select 7,address_typ('NDYgUG9kZGl2aWxsZSBBdmVudWU=','U3QgSXZlcw==','Q29ybndhbGw=','VUs=','VFIyNiA5TlM='),'U2VtaS1EZXRhY2hlZA==','QXZhaWxhYmxlIHRvIFJlbnQ=',2,290 From DUAL
  Union All Select 8,address_typ('OSBIYW5kbGV5IFJvYWQ=','RGVyYnk=','RGVyYnlzaGlyZQ==','VUs=','REUzIDlERg=='),'RGV0YWNoZWQ=','QXZhaWxhYmxlIHRvIFJlbnQ=',1,470 From DUAL
  Union All Select 9,address_typ('NDggQnV4dG9uIFJvYWQ=','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA1VUQ='),'RGV0YWNoZWQ=','QXZhaWxhYmxlIHRvIFJlbnQ=',4,360 From DUAL
  Union All Select 10,address_typ('NTkgU3BvY2t0b3duIENsb3Nl','U2FsZm9yZA==','R3JlYXRlciBNYW5jaGVzdGVy','VUs=','TTEgM0hK'),'U2VtaS1EZXRhY2hlZA==','QXZhaWxhYmxlIHRvIFJlbnQ=',5,310 From DUAL;
  
Insert Into MAINTANENCE_TBL(MAINTANENCE_ID,HOUSE_ID,DESCRIPTION,SEVERITY,DATEOCCURRED,DATECOMPLETED)
  Select 1,4,'Qm9pbGVyIHJlcGxhY2VkIGR1ZSB0byBtYWxmdW5jdGlvbg==','SGlnaA==','MjEvMy8yMDE1','Tm90IENvbXBsZXRlZA==' From DUAL
  Union All Select 2,1,'UmVwYWludGVkIGxpdmluZyByb29tIGR1ZSB0byBzbGlnaHQgZGFtcCBvbiBjZWlsaW5n','TG93','MjIvMy8yMDE1','MjIvMy8yMDE1' From DUAL
  Union All Select 3,4,'Rml0dGVkIG5ldyBkb29ycyB0byByZXBsYWNlIGJyb2tlbiBvbmVz','TG93','MjIvMy8yMDE1','MjIvMy8yMDE1' From DUAL;
  
Insert Into PERSON_TBL(PERSON_ID,FIRSTNAME,SURNAME,PERSON_ADDRESS,CLIENT_INFO)
  Select 2,'V2lsbGlhbQ==','Q2Fycg==',address_typ('MjMgQnV4dG9uIFJvYWQ=','QnV4dG9u','RGVyYnlzaGlyZQ==','VUs=','U0s3IDlIUw=='),client_typ('U2luZ2xl',0) From DUAL
  Union All Select 4,'VGhvbWFz','QmVhc2xleQ==',address_typ('MTYgUGljYXJkIFBsYWNl','QnV4dG9u','RGVyYnlzaGlyZQ==','VUs=','U0s3IDZOUw=='),client_typ('TWFycmllZA==',2) From DUAL
  Union All Select 5,'UmVlY2U=','SW1pb2xlaw==',address_typ('MzcgSGFuZGZvcnRoIFN0cmVldA==','SGFuZGZvcnRo','Q2hlc2hpcmU=','VUs=','U0s5IDJKQQ=='),client_typ('U2luZ2xl',1) From DUAL
  Union All Select 8,'WmFjaA==','UnVkb2w=',address_typ('NTQgV2VsbGluZ3RvbiBSb2Fk','U3RvY2twb3J0','R3JlYXRlciBNYW5jaGVzdGVy','VUs=','yIDZZQg=='),client_typ('U2luZ2xl',1) From DUAL
  Union All Select 10,'Sm9yZGFu','SHlkZQ==',address_typ('MjMgTWNEb25hbGQgU3RyZWV0','U3RvY2twb3J0','R3JlYXRlciBNYW5jaGVzdGVy','VUs=','U0s0IDdCTg=='),client_typ('TWFycmllZA==',0) From DUAL
  Union All Select 14,'TWljaGFlbA==','QmlydGxlcw==',address_typ('MjMgTWNEb25hbGQgU3RyZWV0','U3RvY2twb3J0','R3JlYXRlciBNYW5jaGVzdGVy','VUs=','U0s0IDdCTg=='),client_typ('TWFycmllZA==',3) From DUAL
  Union All Select 15,'U3VsdGFu','Sm9uZXM=',address_typ('NiBNYXBsZSBSb2Fk','Q3JveWRvbg==','TG9uZG9u','VUs=','CR2 10KR'),client_typ('U2luZ2xl',0) From DUAL
  Union All Select 16,'THVrZQ==','Um9wZXI=',address_typ('MjEgUGxvZGRpbmd0b24gTGFuZQ==','RGVyYnk=','RGVyYnlzaGlyZQ==','VUs=','REUzIDVVQw=='),client_typ('TWFycmllZA==',1) From DUAL
  Union All Select 17,'QW1qaGFk','S2hhbg==',address_typ('MjEgUGxvZGRpbmd0b24gTGFuZQ==','RGVyYnk=','RGVyYnlzaGlyZQ==','VUs=','REUzIDVVQw=='),client_typ('U2luZ2xl',0) From DUAL
  Union All Select 20,'TWljaGFlbA==','Q29vbmV5',address_typ('MjMgQ29ycmluYXRpb24gUm9hZA==','U3RvY2twb3J0','R3JlYXRlciBNYW5jaGVzdGVy','VUs=','U0s0IDZKRA=='),client_typ('TWFycmllZA==',2) From DUAL;
  
Insert Into PERSON_TBL(PERSON_ID,FIRSTNAME,SURNAME,PERSON_ADDRESS,STAFF_INFO)
  Select 1,'Q2hyaXM=','R3JhdmVz',address_typ('MTggQ2hpbHRvbiBSb2Fk','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA4U0I='),staff_typ('TWFuYWdlcg==',120000,0) From DUAL
  Union All Select 3,'SmFuZQ==','R3JhdmVz',address_typ('MTggQ2hpbHRvbiBSb2Fk','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA4U0I='),staff_typ('QWdlbnQ=',30000,1) From DUAL
  Union All Select 6,'TWljaGFlbA==','R3JhdmVz',address_typ('NzMgTHVwaW5zIENvdXJ0','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSAxMFBT'),staff_typ('TWFuYWdlcg==',70000,1) From DUAL
  Union All Select 7,'Sm9hbg==','R3JhdmVz',address_typ('MjUgVGVzdCBDcmVhc2VudA==','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA2TlA='),staff_typ('RW5naW5lZXI=',25000,3) From DUAL
  Union All Select 9,'TG91aXNl','R3JhdmVz',address_typ('MTYgV2VudGxvY2sgQ2xvc2U=','V3JleGhhbQ==','Q2x3eWQ=','VUs=','LL11 9KS'),staff_typ('QWdlbnQ=',32000,3) From DUAL    
  Union All Select 11,'RGFuaWVs','R3JhdmVz',address_typ('MjMgU3V0dG9uIEF2ZW51ZQ==','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA0TEc='),staff_typ('RW5naW5lZXI=',27000,1) From DUAL
  Union All Select 12,'THlubmU=','R3JhdmVz',address_typ('MjMgU3V0dG9uIEF2ZW51ZQ==','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA0TEc='),staff_typ('QWdlbnQ=',30000,1) From DUAL
  Union All Select 13,'Um9iZXJ0','R3JhdmVz',address_typ('MjMgU3V0dG9uIEF2ZW51ZQ==','TWFjY2xlc2ZpZWxk','Q2hlc2hpcmU=','VUs=','U0sxMSA0TEc='),staff_typ('QWdlbnQ=',29000,3) From DUAL
  Union All Select 18,'SmFtZXM=','V2lsbGlhbXNvbg==',address_typ('MTYgV2VudGxvY2sgQ2xvc2U=','V3JleGhhbQ==','Q2x3eWQ=','VUs=','TEwxMSA5S1M='),staff_typ('RW5naW5lZXI=',24000,3) From DUAL
  Union All Select 19,'RGFuaWVs','V2lsbGlhbXNvbg==',address_typ('MTYgV2VudGxvY2sgQ2xvc2U=','V3JleGhhbQ==','Q2x3eWQ=','VUs=','TEwxMSA5S1M='),staff_typ('QWdlbnQ=',30000,1) From DUAL;
  
Insert Into SECURE_PERSON_TBL(PERSON_ID,PER_USERNAME,PER_PASSWORD)
  Select 1,'Z3JhdmVzYw==','R3JhdmVzYw==' From DUAL
  Union All Select 2,'Y2FycldpbGw=','UG9rZW1vbjE5OTQ=' From DUAL
  Union All Select 3,'Z3JhdmVzamE=','R3JhdmVzamE=' From DUAL
  Union All Select 4,'QmVhc2xleUJveXM=','QmVhc2xleXQxMA==' From DUAL
  Union All Select 5,'aW1pb2xla19yZWVjZQ==','YW5pbWUxMDE=' From DUAL
  Union All Select 6,'Z3JhdmVzbQ==','R3JhdmVzbQ==' From DUAL
  Union All Select 7,'Z3JhdmVzam8=','R3JhdmVzam8=' From DUAL
  Union All Select 8,'cnVkb2xwaDk0','emFjaGFyeXIy' From DUAL
  Union All Select 9,'Z3JhdmVzbG8=','R3JhdmVzbG8=' From DUAL
  Union All Select 10,'bGFpemVib2k=','bWFudXRkOTE=' From DUAL
  Union All Select 11,'Z3JhdmVzZA==','R3JhdmVzZA==' From DUAL
  Union All Select 12,'Z3JhdmVzbHk=','R3JhdmVzbHk=' From DUAL
  Union All Select 13,'Z3JhdmVzcg==','R3JhdmVzcg==' From DUAL
  Union All Select 14,'YmlydGxlc21pa2U=','bWlrZXliMjM=' From DUAL
  Union All Select 15,'U3VsdGFuSm9uZXM=','c3VsdGFuSjk0' From DUAL
  Union All Select 16,'TF9Sb3Blcg==','bHVrZXJvcGU=' From DUAL
  Union All Select 17,'S2hhbkE=','QW1qaGFkS2hhbjkz' From DUAL
  Union All Select 18,'d2lsbGlhbXNvbmo=','V2lsbGlhbXNvbmo=' From DUAL
  Union All Select 19,'d2lsbGlhbXNvbmQ=','V2lsbGlhbXNvbmQ=' From DUAL
  Union All Select 20,'TUNvb25leQ==','Q29vbmV5TQ==' From DUAL;
  
  commit;