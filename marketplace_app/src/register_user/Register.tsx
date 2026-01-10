function Register()
{
  return (
    <>
      <form method="put" onSubmit={handleSubmit} className="form-example">
        <div className="form-example">
          <label htmlFor="name">Enter your name: </label>
          <input type="text" name="name" id="name" required />
        </div>
        <div className="form-example">
          <label htmlFor="password">Enter your password: </label>
          <input type="password" name="password" id="password" required />
        </div>
        <div className="form-example">
          <label htmlFor="email">Enter your email: </label>
          <input type="email" name="email" id="email" required />
        </div>
        <div className="form-example">
          <input type="submit" value="register" />
        </div>
      </form>
    </>
  );
}

function handleSubmit(e : any)
{
  e.preventDefault();
}

export default Register;