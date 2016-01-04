
package lu.engine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SampleWf {


    public static void main(String ...args) {
        final SampleWf wf = new SampleWf();
        wf.init();
        wf.start();
    }

    private MyNode root = null;

    public void init() {
        root = build("0", "init", null, true);
        MyNode first = build("1", "first", root, true);
        root.childs.add(first);
        MyNode second = build("2", "second", first, true);
        MyNode third = build("3", "third", first, false);
        first.childs.add(second);
        first.childs.add(third);
        MyNode fourth = build("4", "fourth", second, true);
        second.childs.add(fourth);

        System.out.println("init ..." + root);
    }

    public void start() {
        goTo(root);
    }

    protected void goTo(final MyNode node) {
        System.out.println("handle node:" + node.identifier);
        if(!node.condition.isCheck()) {
            System.out.println("condition not check for node:" + node.identifier);
            return;
        }
        for (MyNode children : node.childs) {
            System.out.println("handle children for:" + node.identifier);
            goTo(children);
        }
    }

    protected MyNode build(String id, String name, MyNode root, boolean accept) {
        MyNode node = new MyNode();
        node.identifier = id;
        node.name = name;
        node.root = root;
        node.condition = new Conditional(accept);
        return node;
    }

    public class MyNode {
        public String identifier;
        public String name;
        public MyNode root;
        public Conditional condition;
        public List<MyNode> childs = new ArrayList<MyNode>();

        @Override
        public String toString() {
            final StringBuffer buffer = new StringBuffer("[node=");
            buffer.append(" name:"+ name+",");
            buffer.append(" id:"+identifier+",");
            buffer.append(" condition:"+condition+",");
            buffer.append("\n childs: {");
            for (MyNode c : childs) {
                buffer.append(c+",");
            }
            buffer.append("}]");
            return buffer.toString();
        }
    }

    public class Conditional{

        public boolean check;

        public Conditional(final boolean check) {
            this.check = check;
        }

        public boolean isCheck() {
            return this.check;
        }

        @Override
        public String toString() {
            final StringBuffer buffer = new StringBuffer("[condition=");
            buffer.append(String.format(" value: %s]", check));
            return buffer.toString();
        }

    }

}
