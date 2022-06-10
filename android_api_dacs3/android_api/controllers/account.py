# from android_api_dacs3.android_api.models import Account
# from android_api_dacs3.android_api import db
#
#
# def create_account(userEmail, userPassword):
#     try:
#         account = Account(userEmail, userPassword, "0")
#         if not Account.query.filter_by(userEmail = userEmail).first():
#             db.session.add(account)
#             db.session.commit()
#             return True
#     except Exception as e:
#         print(e)
#         return False
#
#
# def show_all_account():
#     try:
#         account = Account.query.all()
#         data = []
#         for item in account:
#             data.append({
#                 "ID_account": item.id_account,
#                 "userEmail": item.userEmail,
#                 "userPassword": item.userPassword,
#                 "role": item.role
#             })
#         return data
#     except Exception as e:
#         print(e)
#         return None
#
#
# def show_by_id_account(id_account):
#     try:
#         account = Account.query.filter_by(id_account = id_account)
#         data = []
#         for item in account:
#             data.append({
#                 "ID_account": item.id_account,
#                 "userEmail": item.userEmail,
#                 "userPassword": item.userPassword,
#                 "role": item.role
#             })
#         return data
#     except Exception as e:
#         print(e)
#         return None
#
#
# def update_account(id_account, userEmail, userPassword):
#     try:
#         account = Account.query.filter_by(id_account = id_account).first()
#         account.userEmail = userEmail
#         account.userPassword = userPassword
#         db.session.commit()
#         return True
#     except Exception as e:
#         print(e)
#         return False
#
#
# def delete_account(id_account):
#     try:
#         account = Account.query.get(id_account)
#         db.session.delete(account)
#         db.session.commit()
#         return True
#     except Exception as e:
#         print(e)
#         return False
#
