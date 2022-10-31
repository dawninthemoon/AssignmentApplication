# 모바일프로그래밍 개인과제

## 기본 정보
개발 환경: **Android Studio**, JAVA
SDK Version: **Android 12**

### AppUtility.java
```
package com.example.assignmentapplication;  
  
public class AppUtility {  
    public final static String USER_INFO_ID = "USER_INFO";  
	public final static String UID_GUEST = "GUEST";  
	public final static String UID_ID = "UID_";
	public static String CUR_ID = "";
}
```
프레퍼런스를 통한 데이터 관리를 할 때, ID를 손쉽게 관리하기 위해 만들어둔 상수와 전역변수이다.

##  첫 번째 화면
### activity_main.xml
```
<LinearLayout ...
	 android:orientation="vertical">  
	 <TextView ...
	  android:text="로그인하려면\n아이디와 비밀번호를 입력하세요."  >  
	 </TextView>  
	 <EditText  ... 
	  android:hint="아이디">  
	 </EditText> 
	 <EditText ...  
	  android:hint="비밀번호">  
	 </EditText> 
	 <LinearLayout ...
		android:orientation="horizontal">  
		<Button ... 
			android:text="로그인">  
		</Button> 
		<Button ...
			android:text="회원가입">  
		</Button>  
		<Button ...
			android:text="게스트 로그인">  
		</Button> 
	 </LinearLayout>
 </LinearLayout>
```

LinearLayout을 사용해 위에서부터 아래쪽으로 위젯이 배치되도록 만들었다.
비밀번호 아래에 있는 버튼들을 만들 때에도 LinearLayout을 한 번 더 사용했는데, 왼쪽에서부터 오른쪽으로 버튼이 배치되게 하기 위함이다.

### MainActivity.java
```
public class MainActivity extends AppCompatActivity {  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_main);
		...
	}
}
```
첫 번째 화면이므로 AppCompatActivity를 상속받는다.
위의 activity_main.xml의 레이아웃을 따른다.

```
SharedPreferences infoPreference = getSharedPreferences(AppUtility.USER_INFO_ID, MODE_PRIVATE);  
signInBtn.setOnClickListener(new View.OnClickListener() {  
    @Override  
    public void onClick(View view) {  
        String pwStr = infoPreference.getString(String.valueOf(AppUtility.UID_ID + idInput.getText()), null);
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);  
        myAlertBuilder.setTitle("오류");
        myAlertBuilder.setPositiveButton("확인",null);  
        ...
    }
}
```
이 애플리케이션에서는 회원 정보 저장을 프레퍼런스로 관리하고 있다.
앞서 서술한 AppUtility의 상수 필드와 입력된 ID를 이용해 프레퍼런스 정보를 불러온다.
Alert는 안에 들어가는 메시지만 달라지므로 객체를 미리 생성해 두었다.

```
if (pwStr == null) {  
    myAlertBuilder.setMessage("ID가 존재하지 않습니다.");  
	myAlertBuilder.show();  
}  
else if(!pwStr.equals(pwInput.getText().toString())){  
    myAlertBuilder.setMessage("비밀번호가 일치하지 않습니다");  
	myAlertBuilder.show();  
}  
else {  
    Intent intent = new Intent(getApplicationContext(), ShopActivity.class);  
	AppUtility.CUR_ID = idInput.getText().toString();  
	startActivity(intent);  
}
```
각자 맞는 상황에 따라 적절하게 오류 메시지를 띄워주고, 어떠한 오류도 없는 경우 ShopActivity로 이동한다.

```
signUpBtn.setOnClickListener(new View.OnClickListener() {  
    @Override  
	  public void onClick(View view) {  
	      Intent intent = new Intent(getApplicationContext(), SignupActivity.class);  
		  startActivity(intent);  
	  }  
});
```
회원가입 버튼을 눌렀을 경우엔 따로 전달해야 할 정보가 없기 때문에 그냥 넘어간다.

```
Button guestBtn = findViewById(R.id.btn_guest);  
guestBtn.setOnClickListener(new View.OnClickListener() {  
    @Override  
	public void onClick(View view) {  
        Intent intent = new Intent(getApplicationContext(), ShopActivity.class);  
		AppUtility.CUR_ID = AppUtility.UID_GUEST;  
		startActivity(intent);  
	}  
});
```

## 두 번째 화면
### activity_signup.xml
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"...>  
  
 <EditText  ...
  android:hint="아이디">  
 </EditText> <EditText ...
  android:hint="비밀번호">  
 </EditText> <TextView ...
  android:text="비밀번호는 영어, 숫자, 특수문자를 포함하고, 8자에서 20자 사이어야 합니다.">  
 </TextView> <EditText ...
  android:hint="이름">  
 </EditText> <EditText ...
  android:hint="전화번호">  
 </EditText> <EditText ...
  android:hint="주소">  
 </EditText> <TextView ...
  android:text="이용자 식별자 및 제공된 개인정보는 이용자 식별, 통계, 계정 연동 및 CS 등을 위해 서비스 이용기간 동안 활용/보관됩니다. 본 제공 동의를 거부할 권리가 있으나, 동의를 거부하실 경우 서비스 이용이 제한될 수 있습니다."  
  >  
 </TextView> <LinearLayout ...>  
 <RadioGroup  android:layout_width="match_parent"  
  android:gravity="center">  
  
 <RadioButton  android:id="@+id/radio_agree"  
  android:text="동의">  
 </RadioButton>  
 <RadioButton  android:id="@+id/radio_disagree"  
  android:text="미동의">  
 </RadioButton> </RadioGroup> </LinearLayout>  
 <Button  android:id="@+id/btn_signup_apply"  
  android:text="회원가입"  
 </Button>  
</LinearLayout>
```
이 또한 위에서 아래쪽으로 위젯을 배치하기 위해 LinearLayout을 기본으로 하고 있다. 첫 번째 화면과 유사하지만, 동의/미동의를 체크하기 위한 RadioButton이 있는 것이 차이점이다.

### SignupActivity.java
```
  
public class SignupActivity extends Activity {  
    @Override  
  public void onCreate(@Nullable Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_shop);
		...
  }
  ...
 }
```
MainActivity와 유사하지만, 첫 번째 화면이 아니라 다른 액티비티에서 넘어와 사용될 예정이기 때문에 그냥 Activity를 상속받고, savedInstanceState를 매개변수로 받는다.
위의 activity_signup을 뷰로 사용한다.

```
SharedPreferences infoPreference = getSharedPreferences(AppUtility.USER_INFO_ID, MODE_PRIVATE);  
SharedPreferences.Editor infoEditor = infoPreference.edit();
```
getSharedPreferences()와 edit()을 이용해 Editor 객체를 얻는다.
항상 같은 id를 인자로 넣어줄 예정이므로, 실수하지 않게끔 상수 테이블을 사용했다.

```
Button applyButton = (Button)findViewById(R.id.btn_signup_apply);  
applyButton.setOnClickListener(new View.OnClickListener() {  
    @Override  
	public void onClick(View view) {
		...
	}
  ...
}
```
회원가입 버튼을 눌렀을 때 적용할 함수를 세팅한다.

```
if (checkIDExists(infoPreference, inputID.getText().toString())) {  
    myAlertBuilder.setMessage("ID가 이미 존재합니다.");  
	myAlertBuilder.show();  
	return;
}
```
```
private boolean checkIDExists(SharedPreferences pref, String id) {  
	String pwStr = pref.getString(String.valueOf(AppUtility.UID_ID + id), null);  
	return pwStr != null;  
}
```
비밀번호의 정보는 상수 테이블 UID_ID+(id 문자열)에 입력받은 아이디에 저장된다. 따라서 (UID_ID+id)를 키로 한 값이 존재할 경우 중복 아이디이다.

```
if (!checkPWValid(infoPreference, inputPW.getText().toString())) {  
    myAlertBuilder.setMessage("비밀번호는 영어, 숫자, 특수문자를 포함하고, 8자에서 20자 사이어야 합니다.");  
    myAlertBuilder.show();  
    return;
}
```
```
private boolean checkPWValid(SharedPreferences pref, String pw) {  
    Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");  
	Matcher passMatcher = passPattern.matcher(pw);  
	return passMatcher.find();  
}
```
비밀번호 체크는 정규 표현식(Regex)를 사용했다.
영문(a-zA-z), 숫자(\d), 특수문자(\w)를 모두 포함하는 8자에서 20자({8, 20})의 비밀번호를 사용하게끔 규칙을 설정했다.

```
if (radio_disagree.isChecked()) {  
    myAlertBuilder.setMessage("약관에 동의해야 회원가입을 마칠 수 있습니다.");  
	myAlertBuilder.show();  
	return;
 }
```
마지막으로 약관 비동의에 대한 체크를 해주면 끝이다.

```
String infoStr = nameStr + "/" + phoneStr + "/" + addressStr;  

AppUtility.CUR_ID = idStr;

infoEditor.putString(AppUtility.UID_ID + idStr, inputPW.getText().toString());  
infoEditor.putString(AppUtility.USER_INFO_ID + "_" + idStr, infoStr);  
infoEditor.commit();  
  
finish();
```
어떠한 조건에도 걸리는 일 없이 입력을 잘 마쳤다면, '이름/전화번호/주소'의 형태로 프레퍼런스에 저장한다. 나중에 사용할 때는, Split 메서드를 사용해 데이터를 다룰 것이다.

그리고 현재 아이디에 방금 회원가입을 마친 아이디를 설정해두는데, 이는 게스트로 로그인한 다음 유저 정보 버튼을 통해 두 번째 화면으로 넘어왔을 시 로그인이 적용되게 하기 위함이다.

회원가입이 끝나면 finish()를 통해 현재 액티비티를 마친다.

## 세 번째 화면
### activity_shop.xml
### ShopActivity.java
```
public class ShopActivity extends Activity {  
    @Override  
  public void onCreate(@Nullable Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_shop);
		...
  }
  ...
 }
```
두 번째 화면의 SignupActivity와 똑같은 방식으로 초기화를 진행한다.
위의 activity_shop.xml을 뷰로 사용한다.

```
String uid = AppUtility.CUR_ID;  
if (uid.equals(AppUtility.UID_GUEST)) {  
    AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);  
	builder.setTitle("Register");  
	builder.setMessage("회원만 사용할 수 있는 기능입니다. 회원가입 페이지로 넘어가시겠습니까?");  
	builder.setNegativeButton("아니오", null);  
	builder.setPositiveButton("예", new DialogInterface.OnClickListener() {  
        @Override  
		public void onClick(DialogInterface dialogInterface, int i) {  
            Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);  
			startActivity(signupIntent);  
		}  
	});  
	builder.show();  
}
```
'회원 정보' 버튼을 눌렀을 때이다. 현재 게스트로 로그인되어 있을 때에는 회원가입을 하게끔 만들어야 한다.
그리고 우리는 앞서 MainActivity및 SignupActivity에서 ShopActivity로 넘어가는 코드를 살펴보았다. AppUtility의 CUR_ID라는 정적 변수에 현재 로그인된 아이디(로그인된 아이디가 없다면 게스트 아이디)의 정보가 저장되어 있으므로, 이를 통해 로그인이 되어있는지를 판별한다.

```
else {  
      String rawInfoStr = userInfo.getString(AppUtility.USER_INFO_ID + "_" + uid, null);  
	  String[] userInfo = rawInfoStr.split("/");  
	  String message = "이름: " + userInfo[0] + "\n";  
	  message += "전화번호: " + userInfo[1] + "\n";  
	  message += "주소: " + userInfo[2] + "\n";  
	  
	  AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);  
	  builder.setTitle(AppUtility.USER_INFO_ID);  
	  builder.setMessage(message);  
	  builder.setPositiveButton("확인", null);  
	  builder.show();  
}
```
현재 정상적으로 로그인되어 있을 때에는 프레퍼런스에 저장되어 있는 유저의 정보를 띄워준다.
