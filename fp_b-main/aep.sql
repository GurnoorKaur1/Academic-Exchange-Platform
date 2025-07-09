-- Create database
DROP DATABASE IF EXISTS aep;
CREATE DATABASE aep;

-- Use database
USE aep;

-- Users table
CREATE TABLE users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       user_type ENUM('professional', 'institution') NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Academic Professionals table
CREATE TABLE academic_professionals (
                                        professional_id INT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        current_institution VARCHAR(255),
                                        academic_position VARCHAR(255),
                                        education_background TEXT,
                                        area_of_expertise TEXT,
                                        FOREIGN KEY (professional_id) REFERENCES users(user_id)
);

-- Academic Institutions table
CREATE TABLE academic_institutions (
                                       institution_id INT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       address TEXT,
                                       FOREIGN KEY (institution_id) REFERENCES users(user_id)
);

-- Courses table
CREATE TABLE courses (
                         course_id INT PRIMARY KEY AUTO_INCREMENT,
                         institution_id INT,
                         title VARCHAR(255) NOT NULL,
                         code VARCHAR(50) NOT NULL,
                         term VARCHAR(50) NOT NULL,
                         outline TEXT,
                         schedule VARCHAR(255),
                         preferred_qualifications TEXT,
                         delivery_method ENUM('In-Person', 'Remote', 'Hybrid'),
                         compensation DECIMAL(10, 2),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (institution_id) REFERENCES academic_institutions(institution_id)
);

-- Teaching Requests table
CREATE TABLE teaching_requests (
                                   request_id INT PRIMARY KEY AUTO_INCREMENT,
                                   professional_id INT,
                                   course_id INT,
                                   status ENUM('Pending', 'Accepted', 'Rejected') DEFAULT 'Pending',
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   FOREIGN KEY (professional_id) REFERENCES academic_professionals(professional_id),
                                   FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

-- Notifications table
CREATE TABLE notifications (
                               notification_id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT,
                               message TEXT,
                               is_read BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(user_id)
);



-- Insert institutions
INSERT INTO users (email, password, user_type) VALUES
                                                   ('admin@algonquincollege.com', 'password123', 'institution'),
                                                   ('admin@carleton.ca', 'password123', 'institution'),
                                                   ('admin@uottawa.ca', 'password123', 'institution');

INSERT INTO academic_institutions (institution_id, name, address) VALUES
                                                                      (1, 'Algonquin College', '1385 Woodroffe Ave, Nepean, ON K2G 1V8'),
                                                                      (2, 'Carleton University', '1125 Colonel By Dr, Ottawa, ON K1S 5B6'),
                                                                      (3, 'University of Ottawa', '75 Laurier Ave E, Ottawa, ON K1N 6N5');

-- Insert courses
INSERT INTO courses (institution_id, title, code, term, outline, schedule, preferred_qualifications, delivery_method, compensation) VALUES
                                                                                                                                        (1, 'Introduction to Business', 'BUS101', '24F', 'This course covers basic business concepts.', 'Monday, Wednesday 10:00 AM - 11:30 AM', 'MBA or related field', 'In-Person', 5000.00),
                                                                                                                                        (1, 'Web Programming', 'CST8285', '24F', 'Learn to develop dynamic web applications.', 'Tuesday, Thursday 2:00 PM - 4:00 PM', 'MSc in Computer Science', 'Hybrid', 5500.00),
                                                                                                                                        (2, 'Data Structures and Algorithms', 'COMP2402', '24F', 'Advanced programming concepts and techniques.', 'Monday, Wednesday, Friday 1:00 PM - 2:30 PM', 'PhD in Computer Science', 'In-Person', 6000.00),
                                                                                                                                        (2, 'Marketing Management', 'BUSI2204', '24F', 'Principles of marketing and consumer behavior.', 'Tuesday, Thursday 10:00 AM - 11:30 AM', 'PhD in Marketing or MBA', 'Remote', 5800.00),
                                                                                                                                        (3, 'Software Engineering', 'SEG2105', '24F', 'Software development lifecycle and methodologies.', 'Monday, Wednesday 3:00 PM - 4:30 PM', 'MSc or PhD in Software Engineering', 'Hybrid', 6200.00),
                                                                                                                                        (3, 'Financial Accounting', 'ADM1340', '24F', 'Fundamentals of financial accounting.', 'Tuesday, Thursday 1:00 PM - 2:30 PM', 'CPA or PhD in Accounting', 'In-Person', 5700.00);

-- Insert a sample academic professional
INSERT INTO users (email, password, user_type) VALUES
    ('john.doe@example.com', 'password123', 'professional');

INSERT INTO academic_professionals (professional_id, name, current_institution, academic_position, education_background, area_of_expertise) VALUES
    (4, 'John Doe', 'Carleton University', 'Assistant Professor', 'PhD in Computer Science', 'Artificial Intelligence, Machine Learning');


-- Insert a sample teaching request
INSERT INTO teaching_requests (professional_id, course_id, status) VALUES
    (4, 3, 'Pending');

-- Insert a sample notification
INSERT INTO notifications (user_id, message) VALUES
    (4, 'Your application for Data Structures and Algorithms (COMP2402) is under review.');
