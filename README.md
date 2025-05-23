# Kizungu_Shyaka_Noah_Loic
# 🏠 Home Maintenance Scheduling System

Capstone Project for Database Development with PL/SQL (INSY 8311)  
**Author:** Kizungu Shyaka Noah Loic  
**Lecturer:** Eric Maniraguha  
**Academic Year:** 2024–2025  

---

## 📌 Problem Statement

Many homeowners struggle with manually tracking recurring maintenance tasks, leading to delayed repairs and increased costs. There's no centralized system to schedule, assign, and track maintenance jobs or service providers, and existing solutions lack auditability and automation.

---

## 🎯 Project Objectives

- Automate home maintenance scheduling
- Provide a centralized database for tasks, users, and providers
- Enforce business rules using PL/SQL triggers and procedures
- Ensure accountability through auditing
- Prepare the system for future enhancements (notifications, mobile app)

---

## 🧱 Database Design

**Main Entities:**

- `Users`: Homeowners or system admins
- `Tasks`: Maintenance tasks (e.g., plumbing, inspections)
- `Maintenance_Providers`: External service providers
- `Schedule`: Scheduled task records
- `Holidays`: Dates when maintenance is blocked
- `Audit_Log`: Tracks all sensitive operations

**Normalization:** Design follows 3NF  
**ER Diagram:** *(See diagram folder or documentation)*

---

## 💡 Key Features

- Task scheduling via procedure: `PROC_Schedule_Task`
- Retrieve pending tasks via function: `FUNC_Get_Pending_Tasks`
- Enforcement of scheduling rules through triggers
- Auditing user activity in `Audit_Log`
- Handling of real-world constraints (weekdays, holidays)

---

## 🔐 Triggers

**Trigger Name:** `TRG_Schedule_Restrictions`

- Blocks INSERT/UPDATE/DELETE on the `Schedule` table during:
  - Weekdays (Monday to Friday)
  - Official holidays (from `Holidays` table, current month)
- Validates using system date and triggers error if violated

---

## 📋 Auditing

**Table:** `Audit_Log`

| Column        | Description                          |
|---------------|--------------------------------------|
| `log_id`      | Unique ID of audit record            |
| `user_id`     | The user performing the action       |
| `action_type` | INSERT, UPDATE, DELETE               |
| `table_name`  | Name of the table affected           |
| `action_date` | Timestamp of action                  |
| `status`      | Allowed or Denied                    |

> Used to monitor data manipulations and ensure traceability

---

## ⚙️ SQL Execution Overview

### ✅ DDL Operations
- `CREATE`, `ALTER`, and `DROP` tables
- Foreign keys and constraints for data integrity

### ✅ DML Operations
- `INSERT`: Add users, tasks, and schedule entries
- `UPDATE`: Modify task details or user contact info
- `DELETE`: Remove records while enforcing FK constraints

---

## 📊 Results & Testing

- 10+ records inserted across all entities
- Procedures and functions tested with realistic inputs
- All constraints and triggers executed correctly
- Screenshots available in `/screenshots/` folder

---

## ✅ Benefits

- Timely task execution
- Organized provider relationships
- Built-in data validation and access control
- Audit-ready logs for system transparency

---

## 🚧 Challenges & Lessons Learned

- Handling PL/SQL error traps and trigger logic
- Testing date-based constraints (weekday/holiday logic)
- Improved knowledge of cursors, packages, exception handling
- Learned modularization using procedures and functions

---

## 🚀 Future Work

- Integrate email/SMS reminders
- Add a web/mobile frontend for homeowners
- Role-based authentication for multiple users
- Visual analytics dashboard for task frequency and cost

---

## 📁 Repository Structure

