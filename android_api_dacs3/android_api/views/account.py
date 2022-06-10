# from flask import request, jsonify
# from android_api_dacs3.android_api.controllers.account import create_account, show_all_account, show_by_id_account, \
#     update_account, delete_account
# from flask_cors import cross_origin
# from flask import Blueprint
#
# account = Blueprint("account", __name__)
#
#
# @account.route("/api/account/register", methods=["GET", "POST"])
# @account.route("/api/account/create", methods=["GET", "POST"])
# @cross_origin()
# def account_register():
#     res = request.json
#     for key, value in res.items():
#         if value == None:
#             return jsonify({"msg": "add account fail", "error": "bad request"}, 401)
#     if res.get("userPassword") != res.get("userPasswordAgain"):
#         return jsonify({"msg": "wrong password", "data": None}, 401)
#     else:
#         add_status = create_account(
#             userEmail=res.get("userEmail"),
#             userPassword=res.get("userPassword"))
#     if add_status:
#         return jsonify({"msg": "add account success", "data": res}, 200)
#     else:
#         return jsonify({"msg": "no create_account", "data": None}, 401)
#
#
# @account.route("/api/account/read_all", methods=["GET"])
# @cross_origin()
# def account_readAll():
#     acc = show_all_account()
#     if acc == None:
#         return jsonify({"msg": "no account", "data": None}, 500)
#     else:
#         return jsonify({"msg": "get account success", "data": acc}, 200)
#
#
# @account.route("/api/account/read_by_id/<int:id_account>", methods=["GET"])
# @cross_origin()
# def account_readByID(id_account):
#     acc = show_by_id_account(id_account)
#     if acc == None:
#         return jsonify({"msg": "no account", "data": None}, 500)
#     else:
#         return jsonify({"msg": "get account success", "data": acc}, 200)
#
#
# @account.route("/api/account/update/<int:id_account>", methods=["PUT"])
# @cross_origin()
# def account_update(id_account):
#     res = request.json
#     for key, value in res.items():
#         if value == None:
#             return jsonify({"msg": "add account fail", "error": "bad request"}, 401)
#         update_status = update_account(id_account, res.get("userEmail"), res.get("userPassword"))
#     if update_status:
#         return jsonify({"msg": "update account success", "data": res}, 200)
#     else:
#         return jsonify({"msg": "no update", "data": None}, 401)
#
#
# @account.route("/api/account/delete/<int:id_account>", methods=["DELETE"])
# @cross_origin()
# def account_delete(id_account):
#     acc = delete_account(id_account)
#     if acc == None:
#         return jsonify({"msg": "no delete", "data": None}, 500)
#     else:
#         return jsonify({"msg": "delete success", "data": acc}, 200)
