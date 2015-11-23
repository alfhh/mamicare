    DBOperations dao;
    String tag="";

        //databse operations
        dao = new DBOperations(getApplicationContext());

        /*

           // >>>>>>>> patient checks completed <<<<<<<<<<

        Patient patient1 = new Patient("alfa", "01-01-1980", "01-01-2015-030", "photo");
        Patient patient2 = new Patient("beta", "01-02-1950", "01-02-2005-100", "photo");

        /// addPatient
        int idP1 = dao.addPatient(patient1);
        int idP2 = dao.addPatient(patient2);
        Log.d(tag, "idP1 == " + idP1 + " id of created patient");
        Log.d(tag, "idP2 == " + idP2 + " id of created patient");

        /// getAllPatients
        List<Patient> patients = dao.getAllPatients();
        for(Patient patient : patients){
            Log.d(tag, patient.getId() + " id of patients in db");
        }

        /// deletePatient
        boolean patientDeleted = dao.deletePatient(idP2);
        if(patientDeleted){
            Log.d(tag, "patient deleted");
        } else {
            Log.d(tag, "patient not deleted");
        }

        /// getAllPatients && getPatientCount
        patients = dao.getAllPatients();
        for(Patient patient : patients){
            Log.d(tag, patient.getId() + " id of patients in db");
        }
        int patientCount = dao.getPatientCount();
        Log.d(tag, patientCount + " patients in db");

            // >>>>>>>>>> pregnancy tests <<<<<<<<<<<<

        // find patients for testing
        Patient p1 = dao.findPatient(1);
        Log.d(tag, "idp1 == " + p1.getId() + " id of first patient");
        Patient p2 = dao.findPatient(2);
        Log.d(tag, "idp2 == " + p2.getId() + " id of second patient");

        /// addPregnancy
        Pregnancy pr1 = new Pregnancy(0, "10-10-2015");
        Pregnancy pr2 = new Pregnancy(1, "11-10-2015");
        int idpr1 = dao.addPregnancy(p1, pr1);
        int idpr2 = dao.addPregnancy(p2, pr2);

        /// findPregnancy
        pr1 = dao.findPregnancy(idpr1);
        pr2 = dao.findPregnancy(idpr2);
        Log.d(tag, "idPr1 == " + idpr1 + " id of created pregnancy");
        Log.d(tag, "foreign key = " + pr1.getPatient_id());
        Log.d(tag, "idPr2 == " + idpr2 + " id of created pregnancy");
        Log.d(tag, "foreign key = " + pr2.getPatient_id());

        /// deletePregnancy
        boolean pregnancyDeleted = dao.deletePregnancy(idpr1);
        if(pregnancyDeleted){
            Log.d(tag, "pregnancy deleted");
        } else {
            Log.d(tag, "pregnancy not deleted");
        }

        /// getAllPregnanciesFromPatient && getPregnanciesCountFromPatient
        List<Pregnancy> pregnancies = dao.getAllPregnanciesFromPatient(p1);
        for(Pregnancy pregnancy : pregnancies){
            Log.d(tag, pregnancy.getId() + " id of pregnancy in db");
        }
        int p1count = dao.getPregnanciesCountFromPatient(p1);
        Log.d(tag, p1count + "number of pregnancies for p1");
        Log.d(tag, "00000000000000");
        pregnancies = dao.getAllPregnanciesFromPatient(p2);
        for(Pregnancy pregnancy : pregnancies){
            Log.d(tag, pregnancy.getId() + " id of pregnancy in db");
        }
        int p2count = dao.getPregnanciesCountFromPatient(p2);
        Log.d(tag, p2count + "number of pregnancies for p2");

                // >>>>>>>>>> assesment tests <<<<<<<<<<<<

        // getting pregnancies to test
        Pregnancy pr1 = dao.findPregnancy(2);
        Pregnancy pr2 = dao.findPregnancy(3);

        /// addAssesment
        Assesment a1 = new Assesment("06-30-11-10-2015", "07-30-11-10-2015", 80);
        Assesment a2 = new Assesment("10-30-11-10-2015", "12-30-11-10-2015", 85);
        int ida1 = dao.addAssesment(pr1, a1);
        int ida2 = dao.addAssesment(pr2, a2);

        /// findAssesment
        a1 = dao.findAssesment(ida1);
        a2 = dao.findAssesment(ida2);
        Log.d(tag, "ida1 == " + ida1 + " id of created assesment");
        Log.d(tag, "foreign key = " + a1.getPregnancy_id());
        Log.d(tag, "ida2 == " + ida2 + " id of created assesment");
        Log.d(tag, "foreign key = " + a2.getPregnancy_id());

        /// deleteAssesment
        boolean assesmentDeleted = dao.deleteAssesment(ida1);
        if(assesmentDeleted){
            Log.d(tag, "assesment deleted");
        } else {
            Log.d(tag, "assesment not deleted");
        }

        /// getAllAssesmentsFromPregnancy && getAssesmentsCountFromPregnancy
        List<Assesment> assesments = dao.getAllAssesmentsFromPregnancy(pr1);
        for(Assesment assesment : assesments){
            Log.d(tag, assesment.getId() + " id of assesment in db");
        }
        int pr1count = dao.getAssesmentsCountFromPregnancy(pr1);
        Log.d(tag, pr1count + "number of assesments for pr1");
        Log.d(tag, "00000000000000");
        assesments = dao.getAllAssesmentsFromPregnancy(pr2);
        for(Assesment assesment : assesments){
            Log.d(tag, assesment.getId() + " id of assesment in db");
        }
        int pr2count = dao.getAssesmentsCountFromPregnancy(pr2);
        Log.d(tag, pr2count + "number of assesments for pr2");
        
        */

