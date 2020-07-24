from flask import *
import pymysql
app=Flask(__name__)
con=pymysql.connect(host="localhost",user="root",password="root",port=3306,db="virtual_doctor")
cmd=con.cursor()
app.secret_key="abc"


##############ADMIN#######
@app.route('/')
def log():
    return render_template("login.html")
@app.route('/adminhome')
def adminhome():
    return render_template("HOMEPAGE.html")
@app.route('/login',methods=['get','post'])
def login():
    uname = request.form['textfield']
    passwd = request.form['textfield2']
    cmd.execute("select * from login where username='" + uname + "' and password='" + passwd + "'")
    s = cmd.fetchone()
    if s is None:
        return '''<script>alert("invalid user");window.location="/"</script>'''
    elif s[3]=='admin':
        return '''<script>alert("successfully");window.location="/adminhome"</script>'''
    elif s[3]=='hospital':
        session['lid']=s[0]
        return '''<script>alert("successfully");window.location="/hospitalhome"</script>'''
    elif s[3]=='pharmacy':
        session['lid'] = s[0]
        return '''<script>alert("successfully");window.location="/pharmacyhome"</script>'''
    elif s[3] == 'doctor':
        session['lid'] = s[0]
        return '''<script>alert("successfully");window.location="/doctorhome"</script>'''


@app.route('/logout',methods=['get','post'])
def logout():
    return render_template("login.html")


@app.route('/hosp_mang',methods=['get','post'])
def hosp_mang():
    return render_template("hosmanagemnt1.html")
@app.route('/manage_dept',methods=['get','post'])
def manage_dept():
    return render_template("manageDEPT.html")


@app.route('/hosp_mangnt',methods=['get','post'])
def hosp_mangnt():
    return render_template("hosmanagmnt2.html")


@app.route('/dr_view',methods=['get','post'])
def dr_view():
    cmd.execute("select * from doctor_reg")
    s=cmd.fetchall()
    return render_template("DRview.html",val=s)




@app.route('/spec_dep',methods=['get','post'])
def spec_dep():
    return render_template("SPECdeparmnt.html")
@app.route('/viewuser',methods=['get','post'])
def viewuser():
    return render_template("viewuser.html")


@app.route('/viewfeedback')
def viewfeedback():
    cmd.execute("SELECT `patient`.`pname`,`feedback_reg`. `feedback`FROM `feedback_reg` JOIN `patient` ON `feedback_reg`.`lid`=`patient`.`lid`")
    s=cmd.fetchall()
    return render_template("viewfeedback.html",val=s)


@app.route('/addhosp',methods=['get','post'])
def addhosp():
    hname=request.form['textfield']
    hplace=request.form['textfield6']
    hdistrict=request.form['select']
    hpincode=request.form['textfield2']
    hpost=request.form['textfield3']
    hphone=request.form['textfield4']
    hemail=request.form['textfield5']
    cmd.execute("insert into login values(null,'"+hemail+"','"+hphone+"','hospital')")
    login_id=con.insert_id()
    cmd.execute("insert into hospital_reg values(null,'"+str(login_id)+"','"+hname+"','"+hplace+"','"+hpost+"','"+hpincode+"','"+hdistrict+"','"+hphone+"','"+hemail+"')")
    con.commit()
    return '''<script>alert("successfully");window.location="/adminhome"</script>'''

@app.route('/hospital_view',methods=['get','post'])
def hospital_view():
    cmd.execute("select * from hospital_reg")
    s=cmd.fetchall()
    return render_template("hosmanagmnt2.html",val=s)

@app.route('/edithospital',methods=['get','post'])
def edithospital():
    id=request.args.get('id')
    session['id']=id
    cmd.execute("select * from hospital_reg where id='"+str(id)+"'")
    s=cmd.fetchone()
    return render_template("edithospital.html",val=s)

@app.route('/updatehospital',methods=['get','post'])
def updatehospital():
    idd=session['id']
    hname = request.form['textfield']
    hplace = request.form['textfield6']
    hdistrict = request.form['select']
    hpincode = request.form['textfield2']
    hpost = request.form['textfield3']
    hphone = request.form['textfield4']
    hemail = request.form['textfield5']
    cmd.execute("update hospital_reg set hospital_name='"+hname+"',place='"+hplace+"',post='"+hpost+"',pin='"+hpincode+"',district='"+hdistrict+"',phone_number='"+hphone+"',email='"+hemail+"' where id='"+str(idd)+"'")
    con.commit()
    return '''<script>alert("updation successfully completed");window.location="/hospital_view"</script>'''



@app.route('/deletehospital',methods=['get','post'])
def deletehospital():
    id=request.args.get('id')
    cmd.execute("delete from hospital_reg where id='"+str(id)+"'")
    return '''<script>alert("delete successfully completed");window.location="/hospital_view"</script>'''



@app.route('/delete_doctr',methods=['get','post'])
def delete_doctr():
    id=request.args.get('id')
    cmd.execute("delete from doctor_reg where id='"+str(id)+"'")
    con.commit()

    return '''<script>alert("delete doctor successfully completed");window.location="/dr_view"</script>'''





@app.route('/manageDEPT',methods=['get','post'])
def manageDEPT():
    dname=request.form['textfield']
    ddisc=request.form['textarea']

    cmd.execute("insert into department values(null,'"+dname+"','"+ddisc+"')")

    con.commit()
    return '''<script>alert("successfully");window.location="/specs_dep"</script>'''
@app.route('/specs_dep',methods=['get','post'])
def specs_dep():
        cmd.execute("select * from department")
        s=cmd.fetchall()
        return render_template("manageDEPT.html",val=s)

@app.route('/editdepartment',methods=['get','post'])
def editdepartment():
    id=request.args.get('id')
    session['id']=id
    cmd.execute("select * from department where id='"+str(id)+"'")
    s=cmd.fetchone()
    return render_template("editdepartment.html",val=s)


@app.route('/updatedepartment', methods=['get', 'post'])
def updatedepartment():
    id = session['id']
    depname = request.form['textfield']
    depdescription = request.form['textarea']
    cmd.execute("update department set dep_name='"+depname+"',description='"+depdescription+"' where id='"+str(id)+"'")
    con.commit()
    return '''<script>alert("updation successfully completed");window.location="/specs_dep"</script>'''




@app.route('/deletedepartment',methods=['get','post'])
def deletedepartment():
    id=request.args.get('id')
    cmd.execute("delete from department where id='"+str(id)+"'")
    con.commit()
    return '''<script>alert("delete department successfully completed");window.location="/specs_dep"</script>'''

@app.route('/user_view',methods=['get','post'])
def user_view():
    cmd.execute("select * from patient")
    s=cmd.fetchall()
    return render_template('viewuser.html',ss=s)





#########HOSPITAL####################################



@app.route('/hospitalhome',methods=['get','post'])
def hospitalhome():
    return render_template("hpHOME PAGE.html")


@app.route('/dr_reg',methods=['get','post'])
def dr_reg():
    return render_template("drREG.html")


@app.route('/DRREG',methods=['get','post'])
def DRREG():
    DRname=request.form['textfield']
    DRdepartment=request.form['textfield2']
    DRemail=request.form['textfield3']
    DRphone=request.form['textfield4']
    DRplace=request.form['textfield5']
    DRpost=request.form['textfield6']
    DRpin=request.form['textfield7']

    cmd.execute("insert into login values(null,'"+DRemail+"','"+DRphone+"','doctor')")
    login_id=con.insert_id()

    cmd.execute("insert into doctor_reg values(null,'"+str(login_id)+"','"+DRname+"','"+DRdepartment+"','"+DRemail+"','"+DRphone+"','"+DRplace+"','"+DRpost+"','"+DRpin+"','"+str(session['lid'])+"')")
    con.commit()
    return '''<script>alert("registration successfully");window.location="/hospitalhome"</script>'''




@app.route('/dr_manage',methods=['get','post'])
def dr_manage():
    cmd.execute("SELECT * FROM doctor_reg")
    s = cmd.fetchall()
    return render_template("DRmanage.html",val=s)



@app.route('/editdoctor', methods=['get','post'])
def editdoctor():
    id=request.args.get('id')
    session['did']=id
    cmd.execute("select * from doctor_reg where id='"+str(id)+"'")
    s=cmd.fetchone()
    return render_template("dredit.html",val=s)

@app.route('/DRedit',methods=['get','post'])
def DRedit():
    DRname=request.form['textfield']
    DRdepartment=request.form['textfield2']
    DRemail=request.form['textfield3']
    DRphone=request.form['textfield4']
    DRplace=request.form['textfield5']
    DRpost=request.form['textfield6']
    DRpin=request.form['textfield7']

    cmd.execute("update doctor_reg set doctor_name='" + DRname + "',department='" + DRdepartment + "',email='" + DRemail + "',contact_number='" + DRphone + "',place='" + DRplace + "',post='" + DRpost + "',pin='" + DRpin + "' where id='"+str(session['did'])+"'")
    con.commit()

    return '''<script>alert("updated successfully");window.location="dr_manage"</script>'''

@app.route('/delete_doctor', methods=['get','post'])
def delete_doctor():
    id=request.args.get('id')
    session['id']=id
    cmd.execute("delete from doctor_reg where id='"+str(id)+"'")
    con.commit()
    return '''<script>alert("deleted successfully");window.location="dr_manage"</script>'''




@app.route('/H_viewbook',methods=['get','post'])
def dr_viewbook():

    cmd.execute("SELECT`booking`.`status`,`booking`.`date`,`patient`.`pname`,`patient`.`age`,`patient`.`contact` FROM `patient` INNER JOIN `booking` ON `booking`.`user_id`=`patient`.`lid`")
    s=cmd.fetchall()
    return render_template("viewbooking.html",val=s)





@app.route('/H_viewmedi',methods=['get','post'])
def H_viewmedi():

    cmd.execute("SELECT * FROM medicine_reg")
    s=cmd.fetchall()
    return render_template("viewMedicine.html",val=s)


@app.route('/addphy',methods=['get','post'])
def addphy():
    return render_template("pharmacyADD.html")


@app.route('/H_addpharmecy',methods=['get','post'])
def H_addpharmecy():
    pname = request.form['textfield']
    pcontact = request.form['textfield3']
    pemail = request.form['textfield2']

    cmd.execute("insert into login values(null,'" + pname+ "','" + pcontact + "','pharmacy')")
    login_id = con.insert_id()
    cmd.execute("insert into pharmacy_reg values(null,'" + str(login_id) + "','"+str(session['lid'])+"','" + pname + "','" + pemail + "','" + pcontact + "')")
    con.commit()
    return '''<script>alert("pharmacy add addsuccessfully");window.location="/hospitalhome"</script>'''











#################PHARMACY##########################




@app.route('/pharmacyhome',methods=['get','post'])
def pharmacyhome():
    return render_template("phaHOME PAGE.html")



@app.route('/add_medi',methods=['get','post'])
def add_medi():
    return render_template("addMedicine.html")

@app.route('/ADD_MED',methods=['get','post'])
def ADD_MED():
    medicinename=request.form['textfield2']
    description=request.form['textarea2']
    cmd.execute("insert into medicine_reg values(null,'"+str(session['lid'])+"','"+medicinename+"','"+description+"')")
    con.commit()
    return '''<script>alert("medicine add addsuccessfully");window.location="/pharmacyhome"</script>'''











@app.route('/manage_medi',methods=['get','post'])
def manage_medi():
    cmd.execute("select * from medicine_reg where pid='" + str(session['lid']) + "'")
    s=cmd.fetchall()
    return render_template("manageMedicine.html",val=s)


@app.route('/editmedicine',methods=['get','post'])
def editmedicine():
    id=request.args.get('id')
    session['id'] = id
    cmd.execute("select * from medicine_reg where id='"+str(session['id'])+"'")
    s=cmd.fetchone()
    return render_template("editMedicine.html",val=s)

@app.route('/mediedit',methods=['get','post'])
def mediedit():
    mediname=request.form['textfield2']
    medidesc=request.form['textarea2']

    cmd.execute("update medicine_reg set Medicine_Name='" + mediname + "',Description='" + medidesc + "' where id='"+str(session['id'])+"'")
    con.commit()

    return '''<script>alert("updated successfully");window.location="manage_medi"</script>'''




@app.route('/deletemedicine', methods=['get', 'post'])
def deletemedicine():
    id = request.args.get('id')
    session['id'] =id
    cmd.execute("delete from medicine_reg where id='" + str(session['id']) + "'")
    con.commit()

    return '''<script>alert("deleted successfully");window.location="/manage_medi"</script>'''









@app.route('/stock_medi',methods=['get','post'])
def stock_medi():
    cmd.execute("SELECT `stock`.`stock`,`medicine_reg`.`medicine_name` FROM `medicine_reg` JOIN `stock`ON `stock`.`product_id`=`medicine_reg`.`id`")
    s=cmd.fetchall()
    return render_template("manageSTOCK.html",val=s)




@app.route('/stockupda',methods=['get','post'])
def stockupda():
    cmd.execute("select * from medicine_reg where pid='" + str(session['lid']) + "'")
    s = cmd.fetchall()
    return render_template("addStockUpdate.html",val=s)




@app.route('/ADD_stock',methods=['get','post'])
def ADD_stock():
    medicine_id = request.form['select']
    stock = request.form['textfield']
    cmd.execute("select * from stock WHERE `product_id`='"+medicine_id+"'")
    s=cmd.fetchone()
    if s is None:

        cmd.execute("insert into stock values(null,'"+medicine_id+"','"+stock+"')")
        con.commit()
    else:
        cmd.execute("UPDATE `stock` SET `stock`=`stock`+"+stock+" WHERE `product_id`='"+medicine_id+"'")
        con.commit()
    return '''<script>alert("medicine add successfully");window.location="/pharmacyhome"</script>'''

@app.route('/stockupdate',methods=['get','post'])
def stockupdate():
     medicine_name=request.form['select']
     stock=request.form['textfield']

     cmd.execute("update stock SET stock=stock+'" + stock + "' where product_id='"+str(medicine_name)+"'")
     con.commit()
     return '''<script>alert("stock updated successfully");window.location="stock_medi"</script>'''













########################DOCTOR########################


@app.route('/doctorhome',methods=['get','post'])
def doctorhome():

    return render_template("drHOME PAGE.html")








@app.route('/profupd',methods=['get','post'])
def profupd():
    print(str(session['lid']))
    cmd.execute("select * from doctor_reg where did='"+str(session['lid']) + "'")
    s=cmd.fetchone()
    print(s)
    return render_template("profileupdation.html",val=s)


@app.route('/updatedrprof',methods=['get','post'])
def updatedrprof():

    dname = request.form['textfield']
    dcontact = request.form['textfield2']
    demail = request.form['textfield3']
    ddepartment = request.form['textfield4']
    dplace = request.form['textfield5']
    dpost= request.form['textfield6']
    dpin= request.form['textfield7']
    cmd.execute("update doctor_reg set doctor_name='"+dname +"',department='"+ ddepartment+"',email='"+demail+"',contact_number='"+dcontact+"',place='"+dplace+"',post='"+dpost+"',pin='"+ dpin+"' where did='"+str(session['lid'])+"'")
    con.commit()
    return '''<script>alert("updation successfully completed");window.location="/doctorhome"</script>'''











@app.route('/managedisese',methods=['get','post'])
def managedisese():
    cmd.execute("select * from disease_tb ")
    s=cmd.fetchall()
    return render_template("ManageDisease.html",val=s)


@app.route('/addisese',methods=['get','post'])
def addisese():
    return render_template("adddisease.html")

@app.route('/ADDdisese',methods=['get','post'])
def ADDdisese():
    ditype=request.form['select']
    diname=request.form['name']
    disymptoms=request.form['textarea']
    cmd.execute("insert into disease_tb values(null,'"+ditype+"','"+diname+"','"+disymptoms+"')")
    con.commit()
    return '''<script>alert("disese add successfully");window.location="/doctorhome"</script>'''





@app.route('/editdisese',methods=['get','post'])
def editdisese():
    id=request.args.get('id')
    session['id']=id
    cmd.execute("select * from disease_tb where id='"+str(session['id'])+"'")
    s=cmd.fetchone()
    return render_template("editdisease.html",val=s)

@app.route('/diseseedit',methods=['get','post'])
def diseseedit():
    ditype = request.form['select']
    diname = request.form['name']
    disymptoms = request.form['textarea']

    cmd.execute("update disease_tb set type='" + ditype + "',disease='" + diname + "',symptom='" + disymptoms + "' where id='"+str(session['id'])+"'")
    con.commit()
    return '''<script>alert("updated successfully");window.location="managedisese"</script>'''

@app.route('/deletedisese', methods=['get', 'post'])
def deletedisese():
    id = request.args.get('id')
    session['id'] = id
    cmd.execute("delete from disease_tb where id='"+str(session['id'])+"'")
    con.commit()

    return '''<script>alert("deleted successfully");window.location="/managedisese"</script>'''


@app.route('/prescadd',methods=['get','post'])
def prescadd():
    cmd.execute("select * from patient")
    s=cmd.fetchall()
    cmd.execute("select * from disease_tb")
    v=cmd.fetchall()
    return render_template("prescriADD.html",val=s,va=v)



@app.route('/addpresc',methods=['get','post'])
def addpresc():
    return render_template("prescriADD.html")

@app.route('/ADDpresc',methods=['get','post'])
def ADDpresc():
    ppid=request.form['select']
    pdiseaseid=request.form['select2']
    ppriscription = request.form['textarea']
    cmd.execute("insert into priscription values(null,'"+ppid+"','"+pdiseaseid+"','"+ppriscription+"')")
    con.commit()
    return '''<script>alert("priscription add successfully");window.location="/prescadd"</script>'''











@app.route('/viewpatient',methods=['get','post'])
def viewpatient():
    print("SELECT * FROM `patient` JOIN `booking` ON `booking`.`user_id`=`patient`.`lid` JOIN `doctor_reg` ON `doctor_reg`.`did`=`booking`.`doctor_id` WHERE `booking`.`doctor_id`='"+str(session['lid'])+"'")
    cmd.execute("SELECT * FROM `patient` JOIN `booking` ON `booking`.`user_id`=`patient`.`lid` JOIN `doctor_reg` ON `doctor_reg`.`did`=`booking`.`doctor_id` WHERE `booking`.`doctor_id`='"+str(session['lid'])+"'")
    s=cmd.fetchall()
    print(s)
    return render_template("viewPatient.html",val=s)







@app.route('/viewpresc',methods=['get','post'])
def viewpresc():
    cmd.execute("SELECT `patient`.`pname`,`disease_tb`.`disease`,`priscription`.* FROM `priscription` INNER JOIN `disease_tb` ON `disease_tb`.`id`=`priscription`.`disease_id` INNER JOIN `patient` ON `patient`.`lid`=`priscription`.`patient_id`")
    s = cmd.fetchall()
    return render_template("viewprescription.html",val=s)









if __name__=="__main__":
     app.run(debug=True)