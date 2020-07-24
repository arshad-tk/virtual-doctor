import os
from flask import *
import pymysql
from werkzeug.utils import secure_filename
con=pymysql.connect(host='localhost',port=3306,user='root',password='root',db='virtual_doctor')
cmd=con.cursor()


app=Flask(__name__)
app.secret_key='abc'

@app.route('/reg',methods=['post'])
def reg():
    p_name=request.form['name']
    p_age=request.form['age']
    p_gender=request.form['gender']
    p_place=request.form['place']
    p_post=request.form['post']
    p_pin=request.form['pin']
    p_district=request.form['district']
    p_phone=request.form['phone']
    p_email=request.form['e_mail']
    user_name=request.form['user_name']
    password=request.form['password']

    cmd.execute("insert into login values(null,'" + user_name + "','" + password + "','patient')")
    id = con.insert_id()
    cmd.execute("insert into patient values(null,'"+str(id)+"','" +p_name+ "','" + p_gender + "','" + p_age + "','" + p_place + "','" + p_post + "','" + p_pin + "','" + p_district + "','" + p_phone + "','" + p_email + "')")
    con.commit()
    return jsonify({'result':'ok'})

@app.route('/login', methods=['post'])
def login():
        Username = request.form['uname']
        password = request.form['pass']
        print(Username, password)
        cmd.execute("select * from login where username='"+Username+"' and password='"+ password+"'")
        s = cmd.fetchone()
        if s is None:
            return jsonify({'result': "invalid"})
        else:
            print(str(s[0]) + "#" + str(s[3]))
            return jsonify({'result': str(s[0])})



@app.route('/viewdep', methods=['post'])
def viewdep():
    cmd.execute("select * from department")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)




@app.route('/dr_view', methods=['post'])
def dr_view():
    deptid=request.form['deptid']
    print(deptid)
    cmd.execute("SELECT `department`.`Dep_Name`,`doctor_reg`.`id`,`doctor_reg`.`doctor_name`,`doctor_reg`.`contact_number`,doctor_reg.did FROM `doctor_reg` INNER JOIN `department`  ON `department`.`Dep_Name`=`doctor_reg`.`department` where `doctor_reg`.`department`='"+deptid+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)



@app.route('/booking', methods=['post'])
def booking():
    user_id = request.form['user_id']
    doctor_id = request.form['doctor_id']
    date= request.form['date']
    cmd.execute("insert into booking values(null,'"+user_id+"','"+doctor_id+"','"+date+"','pending')")
    con.commit()
    return jsonify({'task': "success"})


@app.route('/viewpres', methods=['post'])
def viewpres():
    id=request.form['pid']
    print(id)
    cmd.execute("SELECT `disease_tb`.`disease`,`priscription`.`priscriptn`FROM `priscription`JOIN`disease_tb`ON`disease_tb`.`id`=`priscription`.`disease_id`WHERE`priscription`.`patient_id`='"+str(id)+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)




@app.route('/viewsym', methods=['post'])
def viewsym():
    type=request.form['ty']
    print(type)
    cmd.execute("select * from disease_tb where  type='"+str(type)+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)



@app.route('/feedback', methods=['post'])
def feedback():
    lid = request.form['id']
    feedback=request.form['feedback']
    cmd.execute("insert into feedback_reg values(null,'"+lid+"','"+feedback+"')")
    con.commit()
    return jsonify({'task': "success"})



if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)