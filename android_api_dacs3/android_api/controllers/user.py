import cloudinary.uploader

from android_api_dacs3.android_api.models import User
from android_api_dacs3.android_api import db


def create_user(userEmail, userPassword, userFirstname, userCity, userAge, userDayofbirth, userImage1, userImage2, userAndress,
                userPhone, userDescription):
    try:
        user = User(userEmail, userPassword, 0, userFirstname, userCity, userAge, userDayofbirth, userImage1, userImage2, userAndress,
                    userPhone, userDescription)
        if not User.query.filter_by(userEmail = userEmail).first():
            db.session.add(user)
            db.session.commit()
            return True
    except Exception as e:
        print(e)
        return False


def login(userEmail, userPassword):
    try:
        data =  User.query.filter_by(userEmail = userEmail, userPassword = userPassword).first()
        if data:
            return data
        else:
            return None
    except Exception as e:
        print(e)
        return False


def show_all_user():
    try:
        user = User.query.all()
        data = []
        for item in user:
            data.append({
                "id_user": item.id_user,
                "userEmail": item.userEmail,
                "userPassword": item.userPassword,
                "role": item.role,
                "userFirstname": item.userFirstname,
                "userCity": item.userCity,
                "userAge": item.userAge,
                "userDayofbirth": item.userDayofbirth,
                "userImage1": item.userImage1,
                "userImage2": item.userImage2,
                "userAndress": item.userAndress,
                "userPhone": item.userPhone,
                "userDescription": item.userDescription
            })
        return data
    except Exception as e:
        print(e)
        return None


def show_by_id_user(id_user):
    try:
        user = User.query.filter_by(id_user = id_user)
        data = []
        for item in user:
            data.append({
                "id_user": item.id_user,
                "userEmail": item.userEmail,
                "userPassword": item.userPassword,
                "role": item.role,
                "userFirstname": item.userFirstname,
                "userCity": item.userCity,
                "userAge": item.userAge,
                "userDayofbirth": item.userDayofbirth,
                "userImage1": item.userImage1,
                "userImage2": item.userImage2,
                "userAndress": item.userAndress,
                "userPhone": item.userPhone,
                "userDescription": item.userDescription
            })
        return data
    except Exception as e:
        print(e)
        return None


def update_user(id_user, userEmail, userPassword, userFirstname,  userDayofbirth, userImage1, userPhone, userDescription):
    try:
        user = User.query.filter_by(id_user = id_user).first()
        cloudinary.config(cloud_name="dqbktgymd", api_key="187563468568422",
                          api_secret="4kUFB1aVl8rf3Tv9kl2nFOSA3vg")
        des_img = user.userImage1
        if des_img:
            cut = des_img.split('/')
            print(cut[len(cut) - 1])
            cloudinary.uploader.destroy(cut[len(cut) - 1])
        user.userEmail = userEmail
        user.userPassword = userPassword
        user.userFirstname = userFirstname
        user.userDayofbirth = userDayofbirth
        user.userImage1 = userImage1
        user.userPhone = userPhone
        user.userDescription = userDescription
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False


def delete_user(id_user):
    try:
        user = User.query.get(id_user)
        db.session.delete(user)
        db.session.commit()
        return True
    except Exception as e:
        print(e)
        return False
