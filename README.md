# AHRQ profile

This is a project that takes an existing FHIR profile for AdverseEvent and modifies it to make it suitable for reporting to AHRQ.

e.g. AdverseEvent.subject can be a Patient.  The Patient profile as specified by ONC (Office of the National Corrdinator) in US Core profile, contains data that fully identifies a patient.  AHRQ cannot use such a profile.  However, there is another profile that is designed to keep the patient unidentified, yet it retains certain data elements that might be of value to AHRQ.  In the AHRQ version of an AdverseEvent profile the subject is specifed as when Patient the Patient resource conforms to the unidentified patient profile.

We shall follow this same pattern for all resources that AdverseEvent references.  i.e. Use a profile that meets AHRQ requirements.  

# AHRQ Profiler

The profiler is a CLI script that loads a set of existing profiles then merges them into a single profile.  