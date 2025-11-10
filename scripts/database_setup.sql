CREATE DATABASE IF NOT EXISTS riwi_codeup_system;
USE riwi_codeup_system;

////////////////////////////////////////////////////////////
// 1. CORE ENTITIES
////////////////////////////////////////////////////////////

-- Coders
CREATE TABLE Coders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    identification VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(15),
    birth_date DATE,
    address TEXT,
    enrollment_date DATE NOT NULL,
    status ENUM('ACTIVE','INACTIVE','GRADUATED','SUSPENDED'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- TeamLeaders
CREATE TABLE TeamLeaders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    identification VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    phone VARCHAR(15),
    specialization VARCHAR(100),
    tech_stack VARCHAR(200),
    hire_date DATE,
    status ENUM('ACTIVE','INACTIVE','ON_LEAVE'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

////////////////////////////////////////////////////////////
// 2. ACADEMIC STRUCTURE
////////////////////////////////////////////////////////////

-- Clans
CREATE TABLE Clans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    name VARCHAR(150),
    description TEXT,
    team_leader_id BIGINT NOT NULL,
    start_date DATE,
    end_date DATE,
    max_coders INT,
    status ENUM('FORMING','ACTIVE','COMPLETED','CANCELLED'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (team_leader_id) REFERENCES TeamLeaders(id)
);

-- Technologies
CREATE TABLE Technologies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE,
    description TEXT,
    category ENUM('LANGUAGE','FRAMEWORK','TOOL','DATABASE'),
    status ENUM('ACTIVE','DEPRECATED'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Modules
CREATE TABLE Modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE,
    name VARCHAR(150),
    description TEXT,
    duration_weeks INT,
    difficulty ENUM('BEGINNER','INTERMEDIATE','ADVANCED','EXPERT'),
    technology_id BIGINT,
    objectives TEXT,
    prerequisites TEXT,
    status ENUM('ACTIVE','INACTIVE','IN_DEVELOPMENT'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (technology_id) REFERENCES Technologies(id)
);

////////////////////////////////////////////////////////////
// 3. CODER PARTICIPATION
////////////////////////////////////////////////////////////

-- ClanAssignments
CREATE TABLE ClanAssignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coder_id BIGINT,
    clan_id BIGINT,
    assignment_date DATE,
    role ENUM('LEADER','MEMBER','MENTOR'),
    status ENUM('ACTIVE','COMPLETED','TRANSFERRED'),
    completion_date DATE,
    evaluation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (coder_id) REFERENCES Coders(id),
    FOREIGN KEY (clan_id) REFERENCES Clans(id)
);

-- ModuleProgress
CREATE TABLE ModuleProgress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    clan_assignment_id BIGINT,
    module_id BIGINT,
    start_date DATE,
    expected_end_date DATE,
    actual_end_date DATE,
    status ENUM('NOT_STARTED','IN_PROGRESS','COMPLETED','BEHIND_SCHEDULE'),
    progress DECIMAL(5,2),
    notes TEXT,
    self_assessment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (clan_assignment_id) REFERENCES ClanAssignments(id),
    FOREIGN KEY (module_id) REFERENCES Modules(id)
);

-- Assessments
CREATE TABLE Assessments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_progress_id BIGINT,
    type ENUM('CODE_REVIEW','QUIZ','PROJECT','PRACTICAL_EXAM','THEORETICAL_EXAM'),
    title VARCHAR(150),
    description TEXT,
    max_score DECIMAL(5,2),
    obtained_score DECIMAL(5,2),
    weight DECIMAL(5,2),
    assessment_date DATE,
    feedback TEXT,
    reviewed_by BIGINT,
    status ENUM('PENDING_REVIEW','PASSED','FAILED','NEEDS_IMPROVEMENT'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (module_progress_id) REFERENCES ModuleProgress(id),
    FOREIGN KEY (reviewed_by) REFERENCES TeamLeaders(id)
);

-- Attendance
CREATE TABLE Attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    clan_assignment_id BIGINT,
    session_date DATE,
    type ENUM('LECTURE','WORKSHOP','PROJECT_WORK','CODE_REVIEW','ONE_ON_ONE'),
    status ENUM('PRESENT','ABSENT','LATE','EXCUSED','REMOTE'),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (clan_assignment_id) REFERENCES ClanAssignments(id)
);

////////////////////////////////////////////////////////////
// 4. SYSTEM ENTITIES
////////////////////////////////////////////////////////////

-- Users
CREATE TABLE Users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(150) UNIQUE,
    password_hash VARCHAR(255),
    role ENUM('ADMIN','TEAM_LEADER','CODER'),
    associated_id BIGINT,
    status ENUM('ACTIVE','INACTIVE','SUSPENDED'),
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- SystemConfig
CREATE TABLE SystemConfig (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    config_key VARCHAR(100) UNIQUE,
    config_value TEXT,
    description TEXT,
    updated_by BIGINT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);