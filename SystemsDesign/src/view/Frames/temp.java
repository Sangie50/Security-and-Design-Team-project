"SELECT module_id FROM student INNER JOIN "
                    + "module_grade ON student.email = module_grade.email INNER"
                    + " JOIN module ON module_grade.module_id = module.module_id"
                    + " INNER JOIN module_teacher ON module.module_id = "
                    + "module_teacher.module_id INNER JOIN teacher ON "
                    + "module_teacher.employee_no = teacher.employee_no WHERE"
                    + " teacher.username = ? AND student.email = ?";