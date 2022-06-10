import os
import uuid

import cloudinary.uploader
from flask import request, jsonify
from android_api_dacs3.android_api.controllers.user import login, create_user, show_all_user, show_by_id_user, delete_user, update_user
from flask_cors import cross_origin
from flask import Blueprint
from werkzeug.utils import secure_filename
from android_api_dacs3.android_api import create_app


user = Blueprint("user", __name__)
app = create_app()


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in app.ALLOWED_EXTENSIONS

@user.route("/api/user/create", methods=["POST"])
@cross_origin()
def user_create():
    # for key, value in res.items():
        if request.method != "POST":
            return jsonify({"msg": "add user fail", "error": "bad request"}, 401)
        else:
            add_status = create_user(
                userEmail=request.form.get("userEmail"),
                userPassword=request.form.get("userPassword"),
                userFirstname=request.form.get("userFirstname"),
                userCity=request.form.get("userCity"),
                userAge=request.form.get("userAge"),
                userDayofbirth=request.form.get("userDayofbirth"),
                userImage1=request.form.get("userImage1"),
                userImage2=request.form.get("userImage2"),
                userAndress=request.form.get("userAndress"),
                userPhone=request.form.get("userPhone"),
                userDescription=request.form.get("userDescription"))
        if add_status:
            return jsonify({"msg": "ok"}), 200
        else:
            return jsonify({"msg": "no create user", "data": None}, 401)


@user.route("/api/user/login", methods=["POST"])
@cross_origin()
def user_login():
    res = request.json
    user = login(
        userEmail=res.get("userEmail"),
        userPassword=res.get("userPassword"))
    if user:
        return jsonify(user), 200
    else:
        return jsonify({"msg": "login fail"}, 401)


@user.route("/api/user/read_all", methods=["GET"])
@cross_origin()
def user_readAll():
    user = show_all_user()
    if user == None:
        return jsonify({"msg": "no user", "data": None}, 500)
    else:
        return jsonify(user), 200


@user.route("/api/user/read_by_id/<int:id_user>", methods=["GET"])
@cross_origin()
def user_readByID(id_user):
    user = show_by_id_user(id_user)
    if user == None:
        return jsonify({"msg": "no user", "data": None}, 500)
    else:
        return jsonify(user), 200


@user.route("/api/user/update/<int:id_user>", methods=["PUT"])
@cross_origin()
def user_update(id_user):
    if request.method != "PUT":
        return jsonify({"msg": "update user fail", "error": "bad request"}, 401)
    else:
        cloudinary.config(cloud_name="dqbktgymd", api_key="187563468568422",
                          api_secret="4kUFB1aVl8rf3Tv9kl2nFOSA3vg")
        pic = request.files['userImage1']
        pic_path = None
        if not pic:
            return "No pic upload", 400
        upload_result = cloudinary.uploader.upload(pic)
        pic_path = upload_result["secure_url"]
        add_status = update_user(
            id_user,
            userEmail=request.form.get("userEmail"),
            userPassword=request.form.get("userPassword"),
            userFirstname=request.form.get("userFirstname"),
            userDayofbirth=request.form.get("userDayofbirth"),
            userImage1=pic_path,
            userPhone=request.form.get("userPhone"),
            userDescription=request.form.get("userDescription"))
    if add_status:
        print(id_user)
        user = show_by_id_user(id_user)
        print(user)
        return jsonify(user), 200
    else:
        return jsonify({"msg": "no update user", "data": None}, 401)
    # res = request.json
    # for key, value in res.items():
    #     if value == None:
    #         return jsonify({"msg": "update user fail", "error": "bad request"}, 401)
    #     update_status = update_user(
    #         id_user,
    #         userEmail=res.get("userEmail"),
    #         userPassword=res.get("userPassword"),
    #         userFirstname=res.get("userFirstname"),
    #         userCity=res.get("userCity"),
    #         userAge=res.get("userAge"),
    #         userDayofbirth=res.get("userDayofbirth"),
    #         userImage1=res.get("userImage1"),
    #         userImage2=res.get("userImage2"),
    #         userAndress=res.get("userAndress"),
    #         userPhone=res.get("userPhone"),
    #         userDescription=res.get("userDescription"))
    # if update_status:
    #     return jsonify({"msg": "update user success", "data": res}, 200)
    # else:
    #     return jsonify({"msg": "no update", "data": None}, 401)


@user.route("/api/user/delete/<int:id_user>", methods=["DELETE"])
@cross_origin()
def user_delete(id_user):
    user = delete_user(id_user)
    if user == None:
        return jsonify({"msg": "no delete", "data": None}, 500)
    else:
        return jsonify({"msg": "delete success", "data": user}, 200)
