    Sub BioPlugInActX_SendResult
        //Return_Value.Value = BioPlugInActX.Result
		form_enroll.biodata.value = BioPlugInActX.Result
    End Sub

    Sub cmd_enroll_finger_OnClick
        Dim newrec
		newrec=Window.document.Forms.frm.newRec1.Value
		//MsgBox "HEY3"
		BioPlugInActX.RegisterPrint newrec
    End Sub

    Sub cmd_identify_singlefinger_OnClick

        BioPlugInActX.IdentifyQuick  "0"
    End Sub

    Sub cmd_verify_singlefinger_OnClick
		Dim verrec
		verrec=Window.document.Forms.frm.verRec1.Value
		//MsgBox  "HEY4"
        BioPlugInActX.VerifyPrint verrec
    End Sub

	Sub BioPlugInActX_OnCapture
		form_enroll.biodata.value = BioPlugInActX.GetSafeFingerData
		form_enroll.submit
	End Sub

	Sub StartCapture
		BioPlugInActX.RegisterSinglePrint "2345"
	End Sub

	Sub CancelCapture
		BioPlugInActX.CancelRegistrationRequest
	End Sub