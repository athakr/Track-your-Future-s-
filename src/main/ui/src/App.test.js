import { render, screen } from '@testing-library/react';
import { shallow, mount } from "enzyme";
import App from './App';


it("renders correctly", () => {
  const wrapper = mount(<App />);
  expect(wrapper.state("error")).toEqual(null);
});

const user = {
  name: "Diddy",
  email: "diddy@thakur.com",
  password: "Computer1!",
};

describe("<App />", () => {
  it("contains account", () => {
    const wrapper = mount(<App user={user} />);
    const value = wrapper.find("p").text();
    expect(value).toEqual("david@gmail.com");
  });
  it("accepts user account props", () => {
    const wrapper = mount(<App user={user} />);
    expect(wrapper.props().user).toEqual(user);
  });
});
 it("renders welcome message", () => {
  const wrapper = shallow(<App />);
  const welcome = <h1>Track Your Future</h1>;
  expect(wrapper.contains(welcome)).toEqual(true);
});
