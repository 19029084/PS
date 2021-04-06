
-- SET NAMES utf8mb4;

-- DEFAULT CHARACTER SET utf8;

-- drop database IF EXISTS PS;

-- create database PS;

-- USE PS;


CREATE TABLE Endpoint
(
	Id INTEGER,
	Scheme TEXT , -- https / http
	Username TEXT,
	Password TEXT,
	Host TEXT,
	Port TEXT,	
	-- host_url VARCHAR(100), -- https://user:password@www.endpoint.com:8088 
	PRIMARY KEY(Id)
);

CREATE TABLE Workspace
(
	Id INTEGER NOT NULL ,
	Name TEXT,
	PRIMARY KEY(ID)	
);

CREATE TABLE Task
( 
	Id INTEGER,
	
	-- WorkspaceId int(11) NOT NULL,
	EndpointId INTEGER NOT NULL,
	-- wID int(11) NOT NULL,
	UniqueKey TEXT,
	Workspace TEXT,
	Project TEXT,
	Stream TEXT,
	Source TEXT,
	Status TEXT,
	CreateDateTime DEFAULT CURRENT_TIMESTAMP,
	Deleted int NOT NULL DEFAULT 0,
	PRIMARY KEY(ID)	
);

CREATE TABLE Stage
( 
	Id INTEGER,
	Name TEXT,
	Command TEXT,
	PRIMARY KEY(ID)	
);



-- insert into Stage(ID,Name) values(1,"create");
-- insert into Stage(ID,Name) values(2,"pending");
-- insert into Stage(ID,Name) values(3,"setup");
-- insert into Stage(ID,Name) values(4,"configure");
-- insert into Stage(ID,Name) values(5,"build");
-- insert into Stage(ID,Name) values(6,"analyze");
-- insert into Stage(ID,Name) values(7,"commit");
-- insert into Stage(ID,Name) values(8,"cleanup");
-- insert into Stage(ID,Name) values(9,"done");

/*
CREATE TABLE `ps_user`
{
	id INT(11),
	endpointId INT(11),
	username VARCHAR(25),
	password VARCHAR(50)	
};






CREATE TABLE `ps_stage`
(
	id INT(11),
	workspaceId INT(11),
	description VARCHAR(25),
	script VARCHAR(200)
);*/
/*
insert into ps_endpoint(id,endpoint) values(1,"http://127.0.0.1:8086");

insert into ps_user(id,endpointId,username,password) values(1,1,"admin","coverity");

insert into ps_workspace(id,endpointId,workspace) values(1,1,"DEMO");

insert into ps_stage(id,workspaceId,description,script) values(1,1,"env","coverity/env.bat");
insert into ps_stage(id,workspaceId,description,script) values(2,1,"configure","coverity/configure.bat");
insert into ps_stage(id,workspaceId,description,script) values(3,1,"build","coverity/build.bat");
insert into ps_stage(id,workspaceId,description,script) values(4,1,"analysis","coverity/analysis.bat");
insert into ps_stage(id,workspaceId,description,script) values(5,1,"commit","coverity/commit.bat");*/






